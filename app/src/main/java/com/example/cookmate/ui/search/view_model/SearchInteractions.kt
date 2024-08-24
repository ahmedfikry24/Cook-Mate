package com.example.cookmate.ui.search.view_model

interface SearchInteractions {
    fun onClickRecipe(id: String)
    fun resetEventsToInitialState()
}