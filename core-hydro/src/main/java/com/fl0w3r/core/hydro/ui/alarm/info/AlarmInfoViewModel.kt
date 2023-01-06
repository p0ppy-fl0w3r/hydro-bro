package com.fl0w3r.core.hydro.ui.alarm.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fl0w3r.core.data.database.HydroDatabase
import com.fl0w3r.core.data.database.entity.ScheduledAlarm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmInfoViewModel @Inject constructor(private val database: HydroDatabase) : ViewModel() {

    private val _alarmItem = MutableLiveData<ScheduledAlarm>()
    val alarmItem: LiveData<ScheduledAlarm>
        get() = _alarmItem

    fun getAlarmItem(alarmId: Int) {
        viewModelScope.launch {
            if (_alarmItem.value == null) {
                val mAlarmItem = database.hydroDao.getAlarm(alarmId)
                _alarmItem.value = mAlarmItem
            }
        }
    }

    fun onAlarmItemChanged(alarmItem: ScheduledAlarm) {
        _alarmItem.value = alarmItem
    }

    fun deleteAlarm(alarmId: Int) {
        viewModelScope.launch {
            database.hydroDao.deleteAlarm(alarmId)
        }
    }

    fun saveAlarm(alarmItem: ScheduledAlarm) {
        viewModelScope.launch {
            database.hydroDao.updateAlarm(alarmItem)
        }
    }
}