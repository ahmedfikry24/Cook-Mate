package com.example.cookmate.ui.home.view_model

import com.example.cookmate.data.model.CategoryDto

data class CategoryInfo(
    val id: String,
    val name: String,
    val onClick: (String) -> Unit,
)

fun CategoryDto.CategoriesItem.toUiSate(onClick: (String) -> Unit): CategoryInfo {
    return CategoryInfo(
        id = this.idCategory ?: "",
        name = this.strCategory ?: "",
        onClick = onClick
    )
}
