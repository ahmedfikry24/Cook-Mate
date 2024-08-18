package com.example.cookmate.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cookmate.data.local.entity.FavouriteRecipeEntity
import com.example.cookmate.data.local.entity.RegisterEntity

@Database(entities = [FavouriteRecipeEntity::class, RegisterEntity::class], version = 1)
abstract class RoomManager : RoomDatabase() {

    abstract val recipeDao: RecipeDao
    abstract val authDao: AuthDao

    companion object {
        @Volatile
        private var instance: RoomManager? = null

        fun getInit(context: Context): RoomManager {
            return instance ?: synchronized(context) {
                return instance ?: Room
                    .databaseBuilder(
                        context.applicationContext,
                        RoomManager::class.java,
                        "infoDataBase"
                    )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
        }

    }
}
