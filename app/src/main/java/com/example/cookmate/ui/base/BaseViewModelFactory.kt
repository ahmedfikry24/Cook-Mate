package com.example.cookmate.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cookmate.data.repository.Repository

class BaseViewModelFactory<V : ViewModel>(
    val repository: Repository,
    private val viewModelClass: Class<V>,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(viewModelClass)) {
            modelClass.getConstructor(Repository::class.java).newInstance(repository)
        } else throw IllegalArgumentException("Unknown ViewModel class")
    }
}
