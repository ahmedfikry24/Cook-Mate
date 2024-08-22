package com.example.cookmate.ui.recipe_details.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cookmate.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeDetailsViewModelFactory(
    private val repository: Repository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Repository::class.java).newInstance(repository)
    }
}


class RecipeDetailsViewModel(
    private val repository: Repository,
) : ViewModel() {


    val recipe = MutableLiveData<RecipeDetails>()

    fun getRecipeInfo(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getMealById(id)
            val isFavorite = repository.getAllFavouriteRecipes().any { it.id == id }
            recipe.postValue(result.map { it.toUiState(isFavorite) }.first())
        }
    }
}
