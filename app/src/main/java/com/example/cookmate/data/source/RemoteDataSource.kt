package com.example.cookmate.data.source

import com.example.cookmate.data.model.CategoryDto
import com.example.cookmate.data.model.MealDto

interface RemoteDataSource {

    suspend fun recipeSearch(name: String): List<MealDto.Recipe>
    suspend fun getMealById(id: String): List<MealDto.Recipe>
    suspend fun getMealsByCategoryName(name: String):List<MealDto.Recipe>
    suspend fun getAllCategories(): List<CategoryDto.CategoriesItem>
    suspend fun getRandomMeal(): List<MealDto.Recipe>
}
