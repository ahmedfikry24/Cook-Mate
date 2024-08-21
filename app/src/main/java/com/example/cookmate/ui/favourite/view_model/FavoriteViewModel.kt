package com.example.cookmate.ui.favourite.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cookmate.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Repository::class.java).newInstance(repository)
    }
}

class FavoriteViewModel(
    private val repository: Repository,
) : ViewModel() {

    val favoriteRecipes = MutableLiveData<List<FavoriteRecipeInfo>>(listOf())
    val events = MutableLiveData<FavoriteEvents>(FavoriteEvents.Idle)

    fun getFavoriteRecipes() {
        viewModelScope.launch(Dispatchers.Default) {
            val result = repository.getAllFavouriteRecipes()
            favoriteRecipes.postValue(
                result.map {
                    it.toUiState(
                        onClickItem = {
                            viewModelScope.launch {
                                events.postValue(FavoriteEvents.OnClickItem(it))
                            }
                        },
                        onClickFavorite = {
                            viewModelScope.launch {
                                events.postValue(FavoriteEvents.OnClickFavorite(it))
                            }
                        }
                    )
                }
            )
        }
    }


    fun removeFavoriteRecipe(id: String) {
        viewModelScope.launch(Dispatchers.Default) {
            repository.removeFavouriteRecipe(id)
            favoriteRecipes.postValue(
                favoriteRecipes.value?.toMutableList()?.filterNot { it.id == id }
            )
        }
    }
}
