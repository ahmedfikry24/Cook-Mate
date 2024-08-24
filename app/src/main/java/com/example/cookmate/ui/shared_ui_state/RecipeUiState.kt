package com.example.cookmate.ui.shared_ui_state

import com.example.cookmate.data.local.entity.FavouriteRecipeEntity
import com.example.cookmate.data.model.MealDto

data class RecipeUiState(
    val id: String = "",
    val name: String = "",
    val instructions: String = "",
    val imageUrl: String = "",
    val videoUrl: String = "",
    val tags: List<String> = listOf(),
    val category: String = "",
    val area: String = "",
    val ingredients: List<String> = listOf(),
    val isFavorite: Boolean = false,
)

fun MealDto.Recipe.toUiState(isFavorite: Boolean = false): RecipeUiState {
    return RecipeUiState(
        id = this.id ?: "",
        name = this.name ?: "",
        instructions = this.instructions ?: "",
        imageUrl = this.imageUrl ?: "",
        videoUrl = this.videoLink ?: "",
        tags = getTagsList(this.tags ?: ""),
        category = this.category ?: "",
        area = this.area ?: "",
        ingredients = getIngredientList(this),
        isFavorite = isFavorite
    )
}

fun FavouriteRecipeEntity.toUiState(): RecipeUiState {
    return RecipeUiState(
        id = this.id,
        name = this.name,
        imageUrl = this.imageUrl
    )
}

fun RecipeUiState.toEntity(): FavouriteRecipeEntity {
    return FavouriteRecipeEntity(
        id = this.id,
        name = this.name,
        imageUrl = this.imageUrl,
        type = this.category,
        area = this.area
    )
}


private fun getTagsList(tag: String): List<String> {
    return tag.split(",").map { it.trim() }
}

private fun getIngredientList(recipe: MealDto.Recipe): List<String> {
    return listOfNotNull(
        recipe.measure1.takeIf { it?.isNotEmpty() == true }?.let { "$it ${recipe.ingredient1}" },
        recipe.measure2.takeIf { it?.isNotEmpty() == true }?.let { "$it ${recipe.ingredient2}" },
        recipe.measure3.takeIf { it?.isNotEmpty() == true }?.let { "$it ${recipe.ingredient3}" },
        recipe.measure4.takeIf { it?.isNotEmpty() == true }?.let { "$it ${recipe.ingredient4}" },
        recipe.measure5.takeIf { it?.isNotEmpty() == true }?.let { "$it ${recipe.ingredient5}" },
        recipe.measure6.takeIf { it?.isNotEmpty() == true }?.let { "$it ${recipe.ingredient6}" },
        recipe.measure7.takeIf { it?.isNotEmpty() == true }?.let { "$it ${recipe.ingredient7}" },
        recipe.measure8.takeIf { it?.isNotEmpty() == true }?.let { "$it ${recipe.ingredient8}" },
        recipe.measure9.takeIf { it?.isNotEmpty() == true }?.let { "$it ${recipe.ingredient9}" },
        recipe.measure10.takeIf { it?.isNotEmpty() == true }?.let { "$it ${recipe.ingredient10}" },
        recipe.measure11.takeIf { it?.isNotEmpty() == true }?.let { "$it ${recipe.ingredient11}" },
        recipe.measure12.takeIf { it?.isNotEmpty() == true }?.let { "$it ${recipe.ingredient12}" },
        recipe.measure13.takeIf { it?.isNotEmpty() == true }?.let { "$it ${recipe.ingredient13}" },
        recipe.measure14.takeIf { it?.isNotEmpty() == true }?.let { "$it ${recipe.ingredient14}" },
        recipe.measure15.takeIf { it?.isNotEmpty() == true }?.let { "$it ${recipe.ingredient15}" },
        recipe.measure16.takeIf { it?.isNotEmpty() == true }?.let { "$it ${recipe.ingredient16}" },
        recipe.measure17.takeIf { it?.isNotEmpty() == true }?.let { "$it ${recipe.ingredient17}" },
        recipe.measure18.takeIf { it?.isNotEmpty() == true }?.let { "$it ${recipe.ingredient18}" },
        recipe.measure19.takeIf { it?.isNotEmpty() == true }?.let { "$it ${recipe.ingredient19}" },
        recipe.measure20.takeIf { it?.isNotEmpty() == true }?.let { "$it ${recipe.ingredient20}" }
    ).filter { it.isNotEmpty() }
}