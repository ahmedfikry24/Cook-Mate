package com.example.cookmate.data.model

import com.google.gson.annotations.SerializedName

data class MealDto(

    @field:SerializedName("meals")
    val meals: List<Recipe?>? = null,
) {
    data class Recipe(

        @field:SerializedName("strYoutube")
        val videoLink: String? = null,

        @field:SerializedName("strCategory")
        val category: String? = null,

        @field:SerializedName("strMealThumb")
        val imageUrl: String? = null,

        @field:SerializedName("strTags")
        val tags: String? = null,

        @field:SerializedName("strArea")
        val strArea: String? = null,

        @field:SerializedName("idMeal")
        val id: String? = null,

        @field:SerializedName("strMeal")
        val name: String? = null,

        @field:SerializedName("strInstructions")
        val instructions: String? = null,
    )
}