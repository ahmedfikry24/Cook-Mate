package com.example.cookmate.data.source

import com.example.cookmate.data.model.CategoryDto
import com.example.cookmate.data.model.MealDto
import com.example.cookmate.data.remote.RetrofitServices

class RemoteDataSourceImpl(private val retrofitServices: RetrofitServices) : RemoteDataSource {

    override suspend fun recipeSearch(name: String): List<MealDto.Recipe> {
        return retrofitServices.recipeSearch(name).body()?.meals.orEmpty().requireNoNulls()
    }

    override suspend fun getMealById(id: String): List<MealDto.Recipe> {
        return retrofitServices.getMealById(id).body()?.meals.orEmpty().requireNoNulls()
    }

    override suspend fun getMealsByCategoryName(name: String): List<MealDto.Recipe> {
        return retrofitServices.getMealsByCategoryName(name).body()?.meals.orEmpty()
            .requireNoNulls()
    }

    override suspend fun getAllCategories(): List<CategoryDto.CategoriesItem> {
        return retrofitServices.getAllCategories().body()?.categories.orEmpty().requireNoNulls()
    }

    override suspend fun getRandomMeal(): List<MealDto.Recipe> {
        return retrofitServices.getRandomMeal().body()?.meals.orEmpty().requireNoNulls()
    }
}
