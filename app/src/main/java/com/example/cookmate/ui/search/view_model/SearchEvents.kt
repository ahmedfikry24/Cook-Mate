package com.example.cookmate.ui.search.view_model

sealed class SearchEvents {
    data object Idle : SearchEvents()
    data object FetchingDataError : SearchEvents()
    data object NoResultMatch : SearchEvents()
    data class OnClickItem(val id: String) : SearchEvents()
}
