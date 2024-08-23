package com.example.cookmate.ui.recipe_details.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookmate.data.repository.Repository
import com.example.cookmate.ui.shared_ui_state.RecipeUiState
import com.example.cookmate.ui.shared_ui_state.toEntity
import com.example.cookmate.ui.shared_ui_state.toUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(
    private val repository: Repository,
) : ViewModel() {

    private val _recipe = MutableLiveData<RecipeUiState>()
    val recipe: LiveData<RecipeUiState> = _recipe

    private var _isFavorite: Boolean = false
    val isFavorite = _isFavorite

    fun getRecipeInfo(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getMealById(id)
            _isFavorite = repository.getAllFavouriteRecipes().any { it.id == id }
            if (result.isNotEmpty())
                _recipe.postValue(result.map { it.toUiState(isFavorite) }.first())
        }
    }

    fun onClickFavorite() {
        viewModelScope.launch {
            if (isFavorite) {
                repository.removeFavouriteRecipe(recipe.value?.id ?: "")
            } else {
                recipe.value?.toEntity()?.let { repository.addFavouriteRecipe(it) }
            }
            _isFavorite = !isFavorite
        }
    }
}
