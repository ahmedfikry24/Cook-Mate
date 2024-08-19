package com.example.cookmate.ui.home.view_model

sealed class HomeEvents {
    data object Idle : HomeEvents()
    data class OnClickCategory(val id: String) : HomeEvents()
}
