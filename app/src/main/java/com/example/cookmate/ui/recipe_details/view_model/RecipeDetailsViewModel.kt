package com.example.cookmate.ui.recipe_details.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cookmate.data.local.entity.FavouriteRecipeEntity
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
    var isFavorite: Boolean = false

    fun getRecipeInfo(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getMealById(id)
            isFavorite = repository.getAllFavouriteRecipes().any { it.id == id }
            if (result.isNotEmpty())
                recipe.postValue(result.map { it.toUiState(isFavorite) }.first())
        }
    }

    fun onClickFavorite() {
        viewModelScope.launch {
            val value = recipe.value
            if (isFavorite) {
                repository.removeFavouriteRecipe(recipe.value?.id ?: "")
            } else {
                repository.addFavouriteRecipe(
                    FavouriteRecipeEntity(
                        id = value?.id ?: "",
                        name = value?.name ?: "",
                        type = value?.category ?: "",
                        area = value?.area ?: "",
                        imageUrl = value?.imageUrl ?: ""
                    )
                )
            }
            isFavorite = !isFavorite
        }
    }
}
