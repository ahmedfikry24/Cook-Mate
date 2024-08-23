package com.example.cookmate.ui.search.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookmate.data.repository.Repository
import com.example.cookmate.ui.shared_ui_state.RecipeUiState
import com.example.cookmate.ui.shared_ui_state.toUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: Repository) : ViewModel(), SearchInteractions {

    private val _recipes = MutableLiveData<List<RecipeUiState>>()
    val recipes: LiveData<List<RecipeUiState>> = _recipes

    private val _events = MutableLiveData<SearchEvents>(SearchEvents.Idle)
    val events: LiveData<SearchEvents> = _events

    fun searchRecipes(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.recipeSearch(query)
                if (result.isEmpty()) _events.postValue(SearchEvents.NoResultMatch)
                _recipes.postValue(result.map { it.toUiState() })
            } catch (e: Exception) {
                _events.postValue(SearchEvents.FetchingDataError)
            }
        }
    }

    override fun onClickRecipe(id: String) {
        _events.postValue(SearchEvents.OnClickItem(id))
    }

    override fun resetEventsToInitialState() {
        _events.postValue(SearchEvents.Idle)
    }
}
