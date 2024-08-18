package com.example.cookmate.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cookmate.data.local.entity.RegisterEntity

@Dao
interface AuthDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: RegisterEntity)

    @Query("SELECT * FROM REGISTERENTITY")
    suspend fun getAllUsers(): List<RegisterEntity>
}