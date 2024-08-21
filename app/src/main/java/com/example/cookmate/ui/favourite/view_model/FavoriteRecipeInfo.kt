package com.example.cookmate.ui.favourite.view_model

import com.example.cookmate.data.local.entity.FavouriteRecipeEntity

data class FavoriteRecipeInfo(
    val id: String = "",
    val name: String = "",
    val category: String = "",
    val area: String = "",
    val url: String = "",
    val onClickItem: (String) -> Unit = {},
    val onClickFavorite: (String) -> Unit = {},
)


fun FavouriteRecipeEntity.toUiState(
    onClickItem: (String) -> Unit,
    onClickFavorite: (String) -> Unit,
): FavoriteRecipeInfo {
    return FavoriteRecipeInfo(
        id = this.id,
        name = this.name,
        category = this.type,
        area = this.area,
        url = this.imageUrl,
        onClickItem = onClickItem,
        onClickFavorite = onClickFavorite
    )
}