package com.example.cookmate.data.repository

import com.example.cookmate.data.local.entity.FavouriteRecipeEntity
import com.example.cookmate.data.local.entity.RegisterEntity
import com.example.cookmate.data.model.CategoryDto
import com.example.cookmate.data.model.MealDto
import com.example.cookmate.data.source.LocalDataSource
import com.example.cookmate.data.source.RemoteDataSource

class RepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : Repository {

    override suspend fun addUser(user: RegisterEntity) {
        localDataSource.addUser(user)
    }

    override suspend fun getAllUsers(): List<RegisterEntity> {
        return localDataSource.getAllUsers()
    }

    override suspend fun addFavouriteRecipe(recipe: FavouriteRecipeEntity) {
        localDataSource.addFavouriteRecipe(recipe)
    }

    override suspend fun getAllFavouriteRecipes(): List<FavouriteRecipeEntity> {
        return localDataSource.getAllFavouriteRecipes()
    }

    override suspend fun removeFavouriteRecipe(recipeId: Int) {
        localDataSource.removeFavouriteRecipe(recipeId)
    }

    override suspend fun recipeSearch(name: String): List<MealDto.Recipe> {
        return remoteDataSource.recipeSearch(name)
    }

    override suspend fun getMealById(id: Int): List<MealDto.Recipe> {
        return remoteDataSource.getMealById(id)
    }

    override suspend fun getAllCategories(): List<CategoryDto.CategoriesItem> {
        return remoteDataSource.getAllCategories()
    }

    override suspend fun getRandomMeal(): List<MealDto.Recipe> {
        return remoteDataSource.getRandomMeal()
    }

}
