package com.fl0w3r.core.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Int = 0,
    val username: String,
    val age: Int,
    val firstName: String,
    val lastName: String,
    val email: String
)
