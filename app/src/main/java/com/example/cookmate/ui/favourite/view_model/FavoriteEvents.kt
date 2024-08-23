package com.example.cookmate.ui.favourite.view_model

sealed class FavoriteEvents {
    data object Idle : FavoriteEvents()
    data class OnClickItem(val id: String) : FavoriteEvents()
}
