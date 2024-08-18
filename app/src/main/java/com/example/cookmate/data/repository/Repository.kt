package com.example.cookmate.data.repository

import com.example.cookmate.data.local.entity.FavouriteRecipeEntity
import com.example.cookmate.data.local.entity.RegisterEntity
import com.example.cookmate.data.model.CategoryDto
import com.example.cookmate.data.model.MealDto

interface Repository {

    suspend fun addUser(user: RegisterEntity)
    suspend fun getAllUsers(): List<RegisterEntity>

    suspend fun addFavouriteRecipe(recipe: FavouriteRecipeEntity)
    suspend fun getAllFavouriteRecipes(): List<FavouriteRecipeEntity>
    suspend fun removeFavouriteRecipe(recipeId: Int)

    suspend fun recipeSearch(name: String): List<MealDto.Recipe>
    suspend fun getMealById(id: Int): List<MealDto.Recipe>
    suspend fun getAllCategories(): List<CategoryDto.CategoriesItem>
    suspend fun getRandomMeal(): List<MealDto.Recipe>

}