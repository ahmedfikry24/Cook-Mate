package com.example.cookmate.ui.home.view_model

sealed class HomeEvents {
    data object Idle : HomeEvents()
    data class OnClickMeal(val id: String) : HomeEvents()
    data object OnCLickFavoriteMore : HomeEvents()
}
