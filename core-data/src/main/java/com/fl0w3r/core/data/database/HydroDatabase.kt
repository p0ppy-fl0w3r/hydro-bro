package com.fl0w3r.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fl0w3r.core.data.database.dao.HydroDao
import com.fl0w3r.core.data.database.entity.ScheduledAlarm
import com.fl0w3r.core.data.database.entity.User

@Database(
    entities = [User::class, ScheduledAlarm::class],
    version = 2,
    exportSchema = false,

    )
@TypeConverters(TypeConvertors::class)
abstract class HydroDatabase : RoomDatabase() {
    abstract val hydroDao: HydroDao
}