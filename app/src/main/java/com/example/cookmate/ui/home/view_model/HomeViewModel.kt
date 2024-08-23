package com.example.cookmate.ui.home.view_model

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

    val categories = MutableLiveData<List<CategoryInfo>>(listOf())
    val categoriesRecipes = MutableLiveData<List<RecipeUiState>>(listOf())
    val recipesOfDay = MutableLiveData<List<RecipeUiState>>(listOf())
    val favoriteRecipes = MutableLiveData<List<RecipeUiState>>(listOf())
    val events = MutableLiveData<HomeEvents>(HomeEvents.Idle)


    fun getDate() {
        getCategories()
        getRecipeOfDay()
        getFavoriteRecipes()
    }

    private fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getAllCategories()
            categories.postValue(result.map { it.toUiSate() })
        }
    }

    private fun getRecipesByCategory(name: String) {
        viewModelScope.launch((Dispatchers.IO)) {
            val result = repository.getMealsByCategoryName(name)
            categoriesRecipes.postValue(result.map { it.toUiState() })
        }
    }

    private fun getRecipeOfDay() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getRandomMeal()
            recipesOfDay.postValue(result.map { it.toUiState() })
        }
    }


    private fun getFavoriteRecipes() {
        viewModelScope.launch(Dispatchers.Default) {
            val result = repository.getAllFavouriteRecipes()
            favoriteRecipes.postValue(result.map { it.toUiState() })
        }
    }

    override fun onClickCategory(name: String) {
        getRecipesByCategory(name)
    }

    override fun onClickRecipe(id: String) {
        viewModelScope.launch { events.postValue(HomeEvents.OnClickMeal(id)) }
    }

    override fun onClickFavoriteMore() {
        viewModelScope.launch { events.postValue(HomeEvents.OnCLickFavoriteMore) }
    }

}
