package com.fl0w3r.core.data.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(indices = [Index(value = ["username"], unique = true)])
data class User(
    @PrimaryKey
    val username: String,
    val age: Int,
    val firstName: String,
    val lastName: String,
    val email: String
)
