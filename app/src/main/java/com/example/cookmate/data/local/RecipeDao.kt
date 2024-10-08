package com.example.cookmate.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cookmate.data.local.entity.FavouriteRecipeEntity

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavouriteRecipe(recipe: FavouriteRecipeEntity)

    @Query("SELECT * FROM FAVOURITERECIPEENTITY")
    suspend fun getAllFavouriteRecipes(): List<FavouriteRecipeEntity>

    @Query("DELETE FROM FAVOURITERECIPEENTITY WHERE id = :recipeId")
    suspend fun removeFavouriteRecipe(recipeId: String)

    @Query("DELETE FROM FAVOURITERECIPEENTITY")
    suspend fun removeAllFavouriteRecipes()
}