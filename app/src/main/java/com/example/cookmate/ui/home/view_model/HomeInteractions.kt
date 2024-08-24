package com.example.cookmate.ui.home.view_model

interface HomeInteractions {

    fun onClickCategory(name: String)
    fun onClickRecipe(id: String)
    fun onClickFavoriteMore()
    fun resetEventToInitialState()
}