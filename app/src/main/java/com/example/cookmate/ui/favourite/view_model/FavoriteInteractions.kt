package com.example.cookmate.ui.favourite.view_model

interface FavoriteInteractions {
    fun onClickItem(id: String)
    fun removeFavoriteRecipe(id: String)
    fun resetEventsToInitialState()
}