package com.example.cookmate.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favouriteRecipeEntity")
data class FavouriteRecipeEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val type: String,
    val area: String,
    val imageUrl: String,
)
