package com.fl0w3r.core.data.database

import androidx.room.TypeConverter
import java.util.Date

class TypeConvertors {

    /**
     * Convert a time stamp to a date object.
     *
     * @param timestamp Time stamp in long
     *
     * @return Date from the provided timestamp
     * */
    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return if (timestamp == null) null else Date(timestamp)
    }


    /**
     * Convert a date to timestamp.
     *
     * @param date Date object that is needed as timestamp.
     *
     * @return Timestamp of the provided date.
     * */
    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }
}