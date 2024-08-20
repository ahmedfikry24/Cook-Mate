package com.example.cookmate.data.source

import com.example.cookmate.data.local.RoomManager
import com.example.cookmate.data.local.entity.FavouriteRecipeEntity
import com.example.cookmate.data.local.entity.RegisterEntity

class LocalDataSourceImpl(private val localDb: RoomManager) : LocalDataSource {
    override suspend fun addUser(user: RegisterEntity) {
        localDb.authDao.addUser(user)
    }

    override suspend fun getAllUsers(): List<RegisterEntity> {
        return localDb.authDao.getAllUsers()
    }

    override suspend fun addFavouriteRecipe(recipe: FavouriteRecipeEntity) {
        localDb.recipeDao.addFavouriteRecipe(recipe)
    }

    override suspend fun getAllFavouriteRecipes(): List<FavouriteRecipeEntity> {
        return localDb.recipeDao.getAllFavouriteRecipes()
    }

    override suspend fun removeFavouriteRecipe(recipeId: String) {
        localDb.recipeDao.removeFavouriteRecipe(recipeId)
    }
}
