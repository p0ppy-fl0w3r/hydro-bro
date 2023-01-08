package com.fl0w3r.core.hydro.ui.alarm.info

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.SaveAs
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fl0w3r.core.data.database.entity.ScheduledAlarm
import com.fl0w3r.core.hydro.broadcast.AlarmReceiver
import com.fl0w3r.core.ui.theme.HydroTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@Composable
fun AlarmInfoScreen(
    alarmId: Int,
    onCompleteUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    infoViewModel: AlarmInfoViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    val alarmItem by infoViewModel.alarmItem.observeAsState()
    // Populate the initial alarm data.
    infoViewModel.getAlarmItem(alarmId)

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager

    if (alarmItem != null) {
        AlarmInfoBody(modifier = modifier, alarmItem = alarmItem!!,

            onAlarmItemChanged = {
                infoViewModel.onAlarmItemChanged(it)
            }, onDeleteClicked = {
                infoViewModel.deleteAlarm(it)
                onCompleteUpdate()
            }, onSaveClicked = {
                infoViewModel.saveAlarm(it)
                updateAlarm(
                    alarmItem!!, alarmManager!!, context
                )
                onCompleteUpdate()
            })
    }
}


@SuppressLint("SimpleDateFormat")
@Composable
fun AlarmInfoBody(
    alarmItem: ScheduledAlarm,
    onAlarmItemChanged: (ScheduledAlarm) -> Unit,
    onSaveClicked: (ScheduledAlarm) -> Unit,
    onDeleteClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(4.dp)) {

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(
                text = SimpleDateFormat("hh:mm aa").format(alarmItem.time),
                style = MaterialTheme.typography.h2
            )
        }

        TextField(value = alarmItem.remarks, onValueChange = {
            onAlarmItemChanged(alarmItem.copy(remarks = it))
        }, label = {
            Text(text = "Remarks")
        }, modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(4.dp))
        Divider()

        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(4.dp)
        ) {
            Text(text = "Recurring:")
            Switch(checked = alarmItem.recurring, onCheckedChange = {
                onAlarmItemChanged(alarmItem.copy(recurring = it))
            })
        }

        Text(
            text = "Should the app remind you of your water intake daily for this alarm?",
            style = MaterialTheme.typography.caption,
            fontStyle = FontStyle.Italic
        )

        Spacer(modifier = Modifier.height(4.dp))
        Divider()

        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(4.dp)
        ) {
            Text(text = "Turn on:")
            Switch(checked = alarmItem.isOn, onCheckedChange = {
                onAlarmItemChanged(alarmItem.copy(isOn = it))
            })
        }
        Text(
            text = "Turn the alarm on or off.",
            style = MaterialTheme.typography.caption,
            fontStyle = FontStyle.Italic
        )

        Spacer(modifier = Modifier.height(4.dp))
        Divider()

        Spacer(modifier = Modifier.height(32.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

            Button(
                onClick = { onDeleteClicked(alarmItem.alarmId) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.error
                )
            ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                Text(text = "Delete")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { onSaveClicked(alarmItem) }) {
                Icon(imageVector = Icons.Default.SaveAs, contentDescription = null)
                Text(text = "Save")
            }
        }

    }
}


private fun updateAlarm(
    alarmItem: ScheduledAlarm, alarmManager: AlarmManager, context: Context
) {

    val alarmIntent = Intent(context, AlarmReceiver::class.java).apply {
        putExtra("remarks", alarmItem.remarks)
    }
    val pendingIntent = PendingIntent.getBroadcast(
        context, alarmItem.alarmId, alarmIntent, PendingIntent.FLAG_IMMUTABLE
    )

    // Cancel any previous alarm
    alarmManager.cancel(pendingIntent)

    if (alarmItem.isOn) {

        val alarmCalender = Calendar.getInstance()
        alarmCalender.time = alarmItem.time

        val calendar: Calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, alarmCalender.get(Calendar.HOUR_OF_DAY))
            set(Calendar.MINUTE, alarmCalender.get(Calendar.MINUTE))
            set(Calendar.SECOND, alarmCalender.get(Calendar.SECOND))
        }

        if (alarmItem.recurring) {
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent
            )
        }
    }
}

@Composable
@Preview
fun AlarmInfoPreview() {
    HydroTheme {
        Surface {
            AlarmInfoBody(
                alarmItem = ScheduledAlarm(
                    alarmId = 1,
                    remarks = "Drink 10 ltrs of water.",
                    time = Date(),
                    createdBy = "Mittens",
                    recurring = false,
                    isOn = false
                ),
                onAlarmItemChanged = {},
                onSaveClicked = {},
                onDeleteClicked = {},
            )
        }

    }
}