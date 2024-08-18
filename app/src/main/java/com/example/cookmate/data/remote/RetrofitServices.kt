package com.example.cookmate.data.remote

import com.example.cookmate.data.model.CategoryDto
import com.example.cookmate.data.model.MealDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitServices {

    @GET("search.php")
    suspend fun recipeSearch(@Query("s") name: String): Response<MealDto>

    @GET("lookup.php")
    suspend fun getMealById(@Query("i") id: Int): Response<MealDto>

    @GET("categories.php")
    suspend fun getAllCategories(): Response<CategoryDto>

    @GET("random.php")
    suspend fun getRandomMeal(): Response<MealDto>
}