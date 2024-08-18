package com.example.cookmate.data.source

import com.example.cookmate.data.local.entity.FavouriteRecipeEntity
import com.example.cookmate.data.local.entity.RegisterEntity

interface LocalDataSource {

    suspend fun addUser(user: RegisterEntity)
    suspend fun getAllUsers(): List<RegisterEntity>

    suspend fun addFavouriteRecipe(recipe: FavouriteRecipeEntity)
    suspend fun getAllFavouriteRecipes(): List<FavouriteRecipeEntity>
    suspend fun removeFavouriteRecipe(recipeId: Int)

}
