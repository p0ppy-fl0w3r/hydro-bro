package com.fl0w3r.core.hydro.ui.alarm.info

import android.view.View
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fl0w3r.core.data.database.entity.ScheduledAlarm
import com.fl0w3r.core.ui.theme.HydroTheme
import java.util.Date

@Composable
fun AlarmInfoScreen(
    alarmId: Int,
    modifier: Modifier = Modifier,
    infoViewModel: AlarmInfoViewModel = hiltViewModel(),
) {
    val alarmItem by infoViewModel.alarmItem.observeAsState()
    infoViewModel.getAlarmItem(alarmId)

    if (alarmItem != null) {
        AlarmInfoBody(modifier = modifier, alarmItem = alarmItem!!)
    }
}

@Composable
fun AlarmInfoBody(alarmItem: ScheduledAlarm, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        TextField(value = "", onValueChange = {}, label = {
            Text(text = "Remarks")
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))
        Divider()

        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(4.dp)
        ) {
            Text(text = "Recurring:")
            Switch(checked = false, onCheckedChange = {})
        }
        Text(
            text = "Should the app remind you of your water intake daily for this alarm?",
            style = MaterialTheme.typography.caption,
            fontStyle = FontStyle.Italic
        )

        Spacer(modifier = Modifier.height(4.dp))
        Divider()

        Spacer(modifier = Modifier.height(32.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

            Button(
                onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.error
                )
            ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                Text(text = "Delete")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.SaveAs, contentDescription = null)
                Text(text = "Save")
            }
        }

    }
}


@Composable
@Preview
fun AlarmInfoPreview() {
    HydroTheme {
        Surface() {
            AlarmInfoBody(
                alarmItem = ScheduledAlarm(
                    alarmId = 1,
                    remarks = "Drink 10 ltrs of water.",
                    time = Date(),
                    createdBy = 1,
                    recurring = false
                )
            )
        }

    }
}