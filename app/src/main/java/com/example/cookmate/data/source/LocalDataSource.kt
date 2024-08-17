package com.example.cookmate.data.source

import com.example.cookmate.data.local.entity.FavouriteRecipeEntity

interface LocalDataSource {

    suspend fun addFavouriteRecipe(recipe: FavouriteRecipeEntity)
    suspend fun getAllFavouriteRecipes(): List<FavouriteRecipeEntity>
    suspend fun removeFavouriteRecipe(recipeId: Int)

}
