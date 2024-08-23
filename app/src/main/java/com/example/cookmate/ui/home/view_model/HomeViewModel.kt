package com.example.cookmate.ui.home.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookmate.data.repository.Repository
import com.example.cookmate.ui.shared_ui_state.RecipeUiState
import com.example.cookmate.ui.shared_ui_state.toUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: Repository,
) : ViewModel(), HomeInteractions {

    private val _categories = MutableLiveData<List<CategoryInfo>>(listOf())
    val categories: LiveData<List<CategoryInfo>> = _categories

    private val _categoriesRecipes = MutableLiveData<List<RecipeUiState>>(listOf())
    val categoriesRecipes: LiveData<List<RecipeUiState>> = _categoriesRecipes

    private val _recipesOfDay = MutableLiveData<List<RecipeUiState>>(listOf())
    val recipesOfDay: LiveData<List<RecipeUiState>> = _recipesOfDay

    private val _favoriteRecipes = MutableLiveData<List<RecipeUiState>>(listOf())
    val favoriteRecipes: LiveData<List<RecipeUiState>> = _favoriteRecipes

    private val _events = MutableLiveData<HomeEvents>(HomeEvents.Idle)
    val events: LiveData<HomeEvents> = _events


    fun getDate() {
        getCategories()
        getRecipeOfDay()
        getFavoriteRecipes()
    }

    private fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getAllCategories()
            _categories.postValue(result.map { it.toUiSate() })
        }
    }

    private fun getRecipesByCategory(name: String) {
        viewModelScope.launch((Dispatchers.IO)) {
            val result = repository.getMealsByCategoryName(name)
            _categoriesRecipes.postValue(result.map { it.toUiState() })
        }
    }

    private fun getRecipeOfDay() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getRandomMeal()
            _recipesOfDay.postValue(result.map { it.toUiState() })
        }
    }


    private fun getFavoriteRecipes() {
        viewModelScope.launch(Dispatchers.Default) {
            val result = repository.getAllFavouriteRecipes()
            _favoriteRecipes.postValue(result.map { it.toUiState() })
        }
    }

    override fun onClickCategory(name: String) {
        getRecipesByCategory(name)
    }

    override fun onClickRecipe(id: String) {
        viewModelScope.launch { _events.postValue(HomeEvents.OnClickMeal(id)) }
    }

    override fun onClickFavoriteMore() {
        viewModelScope.launch { _events.postValue(HomeEvents.OnCLickFavoriteMore) }
    }

    override fun resetEventToInitialState() {
        viewModelScope.launch { _events.postValue(HomeEvents.Idle) }
    }
}
