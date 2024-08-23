package com.example.cookmate.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookmate.data.model.MealDto
import com.example.cookmate.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: Repository) : ViewModel() {

    private val _recipes = MutableLiveData<List<MealDto.Recipe>>()
    val recipes: LiveData<List<MealDto.Recipe>> = _recipes

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _uiEvent = MutableLiveData<String?>()
    val uiEvent: LiveData<String?> = _uiEvent

    fun onSearchViewClicked(query: String) {
        if (query.isNotEmpty()) {
            searchRecipes(query)
        }
    }

    fun searchRecipes(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.recipeSearch(query)
                _recipes.postValue(result)
            } catch (e: Exception) {
                _errorMessage.postValue("Error fetching data")
            }
        }
    }

    fun notifyUser(message: String) {
        _uiEvent.value = message
    }

    fun clearUiEvent() {
        _uiEvent.value = null
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}
