package com.example.cookmate.ui.favourite.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cookmate.data.repository.Repository
import com.example.cookmate.ui.shared_ui_state.RecipeUiState
import com.example.cookmate.ui.shared_ui_state.toUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: Repository,
) : ViewModel() {

    private val _favoriteRecipes = MutableLiveData<List<RecipeUiState>>(listOf())
    val favoriteRecipes: LiveData<List<RecipeUiState>> = _favoriteRecipes

    private val _events = MutableLiveData<FavoriteEvents>(FavoriteEvents.Idle)
    val events: LiveData<FavoriteEvents> = _events

    fun getFavoriteRecipes() {
        viewModelScope.launch(Dispatchers.Default) {
            val result = repository.getAllFavouriteRecipes()
            _favoriteRecipes.postValue(result.map { it.toUiState() })
        }
    }


    fun removeFavoriteRecipe(id: String) {
        viewModelScope.launch(Dispatchers.Default) {
            repository.removeFavouriteRecipe(id)
            _favoriteRecipes.postValue(
                favoriteRecipes.value?.toMutableList()?.filterNot { it.id == id }
            )
        }
    }
}
