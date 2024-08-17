package com.example.cookmate.data.source

import com.example.cookmate.data.local.RecipeDao
import com.example.cookmate.data.local.entity.FavouriteRecipeEntity

class LocalDataSourceImpl(private val dao: RecipeDao) : LocalDataSource {

    override suspend fun addFavouriteRecipe(recipe: FavouriteRecipeEntity) {
        dao.addFavouriteRecipe(recipe)
    }

    override suspend fun getAllFavouriteRecipes(): List<FavouriteRecipeEntity> {
        return dao.getAllFavouriteRecipes()
    }

    override suspend fun removeFavouriteRecipe(recipeId: Int) {
        dao.removeFavouriteRecipe(recipeId)
    }
}
