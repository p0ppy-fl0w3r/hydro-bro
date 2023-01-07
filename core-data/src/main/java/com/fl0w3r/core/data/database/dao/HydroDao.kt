package com.fl0w3r.core.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.fl0w3r.core.data.database.entity.ScheduledAlarm
import com.fl0w3r.core.data.database.entity.User


@Dao
interface HydroDao {
    @Insert
    suspend fun addNewAlarm(scheduledAlarm: ScheduledAlarm)

    @Update
    suspend fun updateAlarm(scheduledAlarm: ScheduledAlarm)

    @Query("DELETE FROM ScheduledAlarm WHERE alarmId=:alarmId")
    suspend fun deleteAlarm(alarmId: Int)

    @Query("SELECT * FROM ScheduledAlarm WHERE alarmId=:alarmId")
    suspend fun getAlarm(alarmId: Int): ScheduledAlarm

    @Query("SELECT * FROM ScheduledAlarm WHERE createdBy=:username")
    suspend fun getAllAlarms(username: String): List<ScheduledAlarm>

    @Query("SELECT * FROM User WHERE username=:username")
    suspend fun getUser(username: String): User

    @Query("DELETE FROM User")
    suspend fun deleteAllUsers()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: User): Long

}
