package com.fl0w3r.core.hydro.ui.alarm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fl0w3r.core.data.database.HydroDatabase
import com.fl0w3r.core.data.database.entity.ScheduledAlarm
import com.fl0w3r.core.data.datastore.HydroPreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val hydroPreferenceRepository: HydroPreferenceRepository,
    private val database: HydroDatabase
) : ViewModel() {
    private val _alarmList = MutableLiveData<List<ScheduledAlarm>>()
    val alarmList: LiveData<List<ScheduledAlarm>>
        get() = _alarmList

    init {
        getAlarmList()
    }

    private fun getAlarmList() {
        viewModelScope.launch {
            hydroPreferenceRepository.loggedInUser.collectLatest {
                getAlarmList(it)
            }
        }
    }

    private suspend fun getAlarmList(userId: Int) {

        val allUserAlarms = database.hydroDao.getAllAlarms(userId)
        _alarmList.value = allUserAlarms

    }

    fun addAlarm(hour: Int, minutes: Int, seconds: Int) {
        viewModelScope.launch {
            hydroPreferenceRepository.loggedInUser.collectLatest {

                val alarmCalender = Calendar.getInstance().apply {
                    set(0, 0, 0, hour, minutes, seconds)
                }
                val alarmDate = alarmCalender.time

                val scheduledAlarm = ScheduledAlarm(
                    remarks = "",
                    time = alarmDate,
                    createdBy = it,
                    recurring = false,
                    isOn = false
                )
                database.hydroDao.addNewAlarm(scheduledAlarm)

                // Update the original alarm list.
                getAlarmList(it)
            }
        }
    }
}