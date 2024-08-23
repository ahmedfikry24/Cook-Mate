package com.example.cookmate.ui.home.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookmate.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: Repository,
) : ViewModel(), HomeInteractions {

    val categories = MutableLiveData<List<CategoryInfo>>(listOf())
    val categoriesRecipes = MutableLiveData<List<RecipeInfo>>(listOf())
    val recipesOfDay = MutableLiveData<List<RecipeInfo>>(listOf())
    val favoriteRecipes = MutableLiveData<List<RecipeInfo>>(listOf())
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

    fun getMealsByCategory(name: String) {
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
        viewModelScope.launch { events.postValue(HomeEvents.OnClickCategory(name)) }
    }

    override fun onClickRecipe(id: String) {
        viewModelScope.launch { events.postValue(HomeEvents.OnClickMeal(id)) }
    }

    override fun onClickFavoriteMore() {
        viewModelScope.launch { events.postValue(HomeEvents.OnCLickFavoriteMore) }
    }

}
