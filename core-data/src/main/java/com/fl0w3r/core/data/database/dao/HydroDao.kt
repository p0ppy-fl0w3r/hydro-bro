package com.fl0w3r.core.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.fl0w3r.core.data.database.entity.ScheduledAlarm
import com.fl0w3r.core.data.database.entity.User


@Dao
interface HydroDao {
    @Insert
    suspend fun addNewAlarm(scheduledAlarm: ScheduledAlarm)

    @Update
    suspend fun updateAlarm(scheduledAlarm: ScheduledAlarm)

    @Delete
    suspend fun deleteAlarm(scheduledAlarm: ScheduledAlarm)

    @Query("SELECT * FROM ScheduledAlarm")
    suspend fun getAllAlarms(): List<ScheduledAlarm>

    @Query("SELECT * FROM ScheduledAlarm WHERE createdBy=:userId")
    suspend fun getAllAlarms(userId: Int): List<ScheduledAlarm>

    // TODO Get user.
    @Query("SELECT * FROM User")
    suspend fun getAllUsers(): List<User>

    @Query("DELETE FROM User")
    suspend fun deleteAllUsers()

    @Insert
    suspend fun addUser(user: User): Long

}
