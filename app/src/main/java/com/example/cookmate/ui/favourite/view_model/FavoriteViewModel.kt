package com.example.cookmate.ui.favourite.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cookmate.data.repository.Repository

class FavoriteViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Repository::class.java).newInstance(repository)
    }
}

class FavoriteViewModel(
    private val repository: Repository,
) : ViewModel() {


}
