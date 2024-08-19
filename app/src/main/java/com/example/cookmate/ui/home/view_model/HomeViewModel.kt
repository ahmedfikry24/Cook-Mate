package com.example.cookmate.ui.home.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cookmate.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Repository::class.java).newInstance(repository)
    }
}

class HomeViewModel(
    private val repository: Repository,
) : ViewModel() {

    val categories = MutableLiveData<List<CategoryInfo>>(listOf())
    val events = MutableLiveData<HomeEvents>(HomeEvents.Idle)

    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getAllCategories()
            categories.postValue(
                result.map {
                    it.toUiSate {
                        viewModelScope.launch {
                            events.postValue(
                                HomeEvents.OnClickCategory(
                                    it
                                )
                            )
                        }
                    }
                }
            )
        }
    }


}
