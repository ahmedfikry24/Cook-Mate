package com.example.cookmate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cookmate.data.repository.Repository
import kotlinx.coroutines.launch

class RecipeActivityViewModelFactory(val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Repository::class.java).newInstance(repository)
    }
}

class RecipeActivityViewModel(
    private val repository: Repository,
) : ViewModel() {

    fun clearFavorites() {
        viewModelScope.launch {
            repository.clearFavourites()
        }
    }
}
