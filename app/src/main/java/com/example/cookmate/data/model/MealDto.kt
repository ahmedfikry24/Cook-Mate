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
        val area: String? = null,

        @field:SerializedName("idMeal")
        val id: String? = null,

        @field:SerializedName("strMeal")
        val name: String? = null,

        @field:SerializedName("strInstructions")
        val instructions: String? = null,

        @field:SerializedName("strMeasure9")
        val measure9: String? = null,

        @field:SerializedName("strMeasure7")
        val measure7: String? = null,

        @field:SerializedName("strMeasure8")
        val measure8: String? = null,

        @field:SerializedName("strMeasure5")
        val measure5: String? = null,

        @field:SerializedName("strMeasure6")
        val measure6: String? = null,

        @field:SerializedName("strMeasure3")
        val measure3: String? = null,

        @field:SerializedName("strMeasure4")
        val measure4: String? = null,

        @field:SerializedName("strMeasure1")
        val measure1: String? = null,

        @field:SerializedName("strMeasure18")
        val measure18: String? = null,

        @field:SerializedName("strMeasure2")
        val measure2: String? = null,

        @field:SerializedName("strMeasure19")
        val measure19: String? = null,

        @field:SerializedName("strMeasure16")
        val measure16: String? = null,

        @field:SerializedName("strMeasure17")
        val measure17: String? = null,

        @field:SerializedName("strMeasure14")
        val measure14: String? = null,

        @field:SerializedName("strMeasure15")
        val measure15: String? = null,

        @field:SerializedName("strMeasure12")
        val measure12: String? = null,

        @field:SerializedName("strMeasure13")
        val measure13: String? = null,

        @field:SerializedName("strMeasure10")
        val measure10: String? = null,

        @field:SerializedName("strMeasure11")
        val measure11: String? = null,

        @field:SerializedName("strIngredient1")
        val ingredient1: String? = null,

        @field:SerializedName("strIngredient3")
        val ingredient3: String? = null,

        @field:SerializedName("strIngredient2")
        val ingredient2: String? = null,

        @field:SerializedName("strIngredient20")
        val ingredient20: String? = null,

        @field:SerializedName("strIngredient5")
        val ingredient5: String? = null,

        @field:SerializedName("strIngredient4")
        val ingredient4: String? = null,

        @field:SerializedName("strIngredient7")
        val ingredient7: String? = null,

        @field:SerializedName("strIngredient6")
        val ingredient6: String? = null,

        @field:SerializedName("strIngredient9")
        val ingredient9: String? = null,

        @field:SerializedName("strIngredient8")
        val ingredient8: String? = null,

        @field:SerializedName("strMeasure20")
        val measure20: String? = null,
        @field:SerializedName("strIngredient10")
        val ingredient10: String? = null,

        @field:SerializedName("strIngredient12")
        val ingredient12: String? = null,

        @field:SerializedName("strIngredient11")
        val ingredient11: String? = null,

        @field:SerializedName("strIngredient14")
        val ingredient14: String? = null,

        @field:SerializedName("strIngredient13")
        val ingredient13: String? = null,

        @field:SerializedName("strIngredient16")
        val ingredient16: String? = null,

        @field:SerializedName("strIngredient15")
        val ingredient15: String? = null,

        @field:SerializedName("strIngredient18")
        val ingredient18: String? = null,

        @field:SerializedName("strIngredient19")
        val ingredient19: String? = null,

        @field:SerializedName("strIngredient17")
        val ingredient17: String? = null,
    )
}