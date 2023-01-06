package com.fl0w3r.core.data.database

import androidx.room.TypeConverter
import java.util.Date

class TypeConvertors {
    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return if (timestamp == null) null else Date(timestamp)
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }
}