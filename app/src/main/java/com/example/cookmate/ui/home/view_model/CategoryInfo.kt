package com.example.cookmate.ui.home.view_model

import com.example.cookmate.data.model.CategoryDto

data class CategoryInfo(
    val id: String,
    val name: String,
)

fun CategoryDto.CategoriesItem.toUiSate(): CategoryInfo {
    return CategoryInfo(
        id = this.idCategory ?: "",
        name = this.strCategory ?: ""
    )
}
