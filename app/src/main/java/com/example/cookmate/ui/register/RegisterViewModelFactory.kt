package com.example.cookmate.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cookmate.data.repository.Repository

class RegisterViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(RegisterViewModel::class.java))
             RegisterViewModel(repository) as T
        else
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}
