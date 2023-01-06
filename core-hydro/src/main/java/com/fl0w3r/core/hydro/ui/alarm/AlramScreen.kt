package com.fl0w3r.core.hydro.ui.alarm

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Build.VERSION_CODES
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlarmAdd
import androidx.compose.material.icons.filled.Watch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fl0w3r.core.data.database.entity.ScheduledAlarm
import com.fl0w3r.core.ui.theme.HydroTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


@Composable
fun AlarmScreen(
    modifier: Modifier = Modifier,
    alarmViewModel: AlarmViewModel = hiltViewModel(),
    onAlarmClicked: (Int) -> Unit
) {
    val allAlarms by alarmViewModel.alarmList.observeAsState(listOf())


    AlarmBody(modifier = modifier, allAlarms = allAlarms, onAddAlarm = { hour, minute, seconds ->
        alarmViewModel.addAlarm(hour, minute, seconds)
    }, onAlarmClicked = {
        onAlarmClicked(it)
    })
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AlarmBody(
    modifier: Modifier = Modifier,
    allAlarms: List<ScheduledAlarm>,
    onAddAlarm: (Int, Int, Int) -> Unit,
    onAlarmClicked: (Int) -> Unit
) {
    val notificationPermissionState =
        rememberPermissionState(permission = if (Build.VERSION.SDK_INT == VERSION_CODES.TIRAMISU) Manifest.permission.POST_NOTIFICATIONS else "")


    var showDialog by remember {
        mutableStateOf(false)
    }

    Column(modifier = modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            IconButton(onClick = {
                if (notificationPermissionState.status.isGranted) {
                    showDialog = true
                } else {
                    notificationPermissionState.launchPermissionRequest()
                }
            }) {
                Icon(imageVector = Icons.Default.AlarmAdd, contentDescription = "Add new alarm")
            }
        }

        AlarmList(allAlarms = allAlarms, onAlarmClicked = { onAlarmClicked(it) })
    }

    if (showDialog) {
        Surface(modifier = Modifier.padding(horizontal = 120.dp)) {
            ClockDialog(state = rememberSheetState(visible = true, onCloseRequest = {
                showDialog = false
            }), selection = ClockSelection.HoursMinutesSeconds { hour, minutes, seconds ->
                onAddAlarm(hour, minutes, seconds)
            })
        }
    }
}

@Composable
fun AlarmList(
    allAlarms: List<ScheduledAlarm>,
    modifier: Modifier = Modifier,
    onAlarmClicked: (Int) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(allAlarms) {
            AlarmItem(scheduledAlarm = it, modifier = Modifier.clickable {
                onAlarmClicked(it.alarmId)
            })
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
fun AlarmItem(scheduledAlarm: ScheduledAlarm, modifier: Modifier = Modifier) {
    Card() {

        Column(modifier = modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Watch,
                    contentDescription = null,
                    modifier = Modifier
                        .weight(1f)
                        .height(77.dp),
                    tint = if (scheduledAlarm.recurring) MaterialTheme.colors.secondary else MaterialTheme.colors.onSurface
                )
                Column(modifier = Modifier.weight(2f)) {
                    Text(
                        text = SimpleDateFormat("hh:mm a").format(scheduledAlarm.time),
                        style = MaterialTheme.typography.h2
                    )
                    Text(
                        text = scheduledAlarm.remarks,
                        style = MaterialTheme.typography.subtitle1,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

            }
        }
    }
}

@Composable
@Preview
fun AlarmItemPreview() {

    val alarmCalender = Calendar.getInstance().apply {
        set(0, 0, 0, 20, 25, 11)
    }
    val alarmDate = alarmCalender.time

    HydroTheme {
        Surface() {
            AlarmItem(
                scheduledAlarm = ScheduledAlarm(
                    alarmId = 1,
                    remarks = "Drink 10 ltrs of water.",
                    time = alarmDate,
                    createdBy = 1,
                    recurring = false
                )
            )
        }
    }
}

@Preview
@Composable
fun AlarmBodyPreview() {

    val listAlarm = listOf(
        ScheduledAlarm(
            alarmId = 1,
            remarks = "Drink 10 ltrs of water.",
            time = Date(),
            createdBy = 1,
            recurring = false
        ),
        ScheduledAlarm(
            alarmId = 2,
            remarks = "Drink 10 ltrs of water Drink 10 ltrs of water.Drink 10 ltrs of water.",
            time = Date(),
            createdBy = 1,
            recurring = true
        )
    )
    HydroTheme {
        Surface {
            AlarmBody(allAlarms = listAlarm, onAddAlarm = { _, _, _ -> }, onAlarmClicked = {})
        }

    }
}