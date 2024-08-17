package com.example.cookmate.data.source

import com.example.cookmate.data.model.CategoryDto
import com.example.cookmate.data.model.MealDto
import com.example.cookmate.data.remote.RetrofitServices

class RemoteDataSourceImpl(private val retrofitServices: RetrofitServices) : RemoteDataSource {

    override suspend fun recipeSearch(name: String): List<MealDto.Recipe> {
        return retrofitServices.recipeSearch(name)
    }

    override suspend fun getMealById(id: Int): List<MealDto.Recipe> {
        return retrofitServices.getMealById(id)
    }

    override suspend fun getAllCategories(): List<CategoryDto.CategoriesItem> {
        return retrofitServices.getAllCategories()
    }

    override suspend fun getRandomMeal(): List<MealDto.Recipe> {
        return retrofitServices.getRandomMeal()
    }
}
