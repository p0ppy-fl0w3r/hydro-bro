package com.fl0w3r.core.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    foreignKeys = [ForeignKey(
        entity = User::class, parentColumns = ["userId"], childColumns = ["createdBy"]
    )]
)
data class ScheduledAlarm(
    @PrimaryKey(autoGenerate = true) val alarmId: Int = 0,
    val remarks: String,
    val time: Date,
    val createdBy: Int,
    val recurring: Boolean
)
