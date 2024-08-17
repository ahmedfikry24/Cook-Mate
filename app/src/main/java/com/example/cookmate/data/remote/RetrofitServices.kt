package com.example.cookmate.data.remote

import com.example.cookmate.data.model.CategoryDto
import com.example.cookmate.data.model.MealDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitServices {

    @GET("search.php")
    suspend fun recipeSearch(@Query("s") name: String): List<MealDto.Recipe>

    @GET("lookup.php")
    suspend fun getMealById(@Query("i") id: Int): List<MealDto.Recipe>

    @GET("categories.php")
    suspend fun getAllCategories(): List<CategoryDto.CategoriesItem>

    @GET("random.php")
    suspend fun getRandomMeal(): List<MealDto.Recipe>
}