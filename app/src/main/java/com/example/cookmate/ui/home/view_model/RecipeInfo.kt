package com.example.cookmate.ui.home.view_model

import com.example.cookmate.data.local.entity.FavouriteRecipeEntity
import com.example.cookmate.data.model.MealDto

data class RecipeInfo(
    val id: String = "",
    val name: String = "",
    val url: String = "",
    val area: String = "",
    val category: String = "",
)

fun MealDto.Recipe.toUiState(): RecipeInfo {
    return RecipeInfo(
        id = this.id ?: "",
        name = this.name ?: "",
        url = this.imageUrl ?: "",
        area = this.strArea ?: "",
        category = this.category ?: ""
    )
}

fun FavouriteRecipeEntity.toUiState(): RecipeInfo {
    return RecipeInfo(
        id = this.id,
        name = this.name,
        url = this.imageUrl
    )
}