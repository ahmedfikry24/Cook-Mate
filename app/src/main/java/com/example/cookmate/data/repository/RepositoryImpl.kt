package com.example.cookmate.data.repository

import com.example.cookmate.data.local.entity.FavouriteRecipeEntity
import com.example.cookmate.data.source.LocalDataSource
import com.example.cookmate.data.source.RemoteDataSource

class RepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : Repository {

    override suspend fun addFavouriteRecipe(recipe: FavouriteRecipeEntity) {
        localDataSource.addFavouriteRecipe(recipe)
    }

    override suspend fun getAllFavouriteRecipes(): List<FavouriteRecipeEntity> {
        return localDataSource.getAllFavouriteRecipes()
    }

    override suspend fun removeFavouriteRecipe(recipeId: Int) {
        localDataSource.removeFavouriteRecipe(recipeId)
    }

}
