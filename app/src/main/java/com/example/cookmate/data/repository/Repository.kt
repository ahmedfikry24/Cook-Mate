package com.example.cookmate.data.repository

import com.example.cookmate.data.local.entity.FavouriteRecipeEntity

interface Repository {

    suspend fun addFavouriteRecipe(recipe: FavouriteRecipeEntity)
    suspend fun getAllFavouriteRecipes(): List<FavouriteRecipeEntity>
    suspend fun removeFavouriteRecipe(recipeId: Int)

}