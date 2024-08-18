package com.example.cookmate.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "registerEntity")
data class RegisterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    val name: String,
    val email: String,
    val password: String,
)
