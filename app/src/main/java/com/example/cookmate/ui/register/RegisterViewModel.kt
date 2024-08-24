package com.example.cookmate.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookmate.data.local.entity.RegisterEntity
import com.example.cookmate.data.repository.Repository
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: Repository) : ViewModel() {

    fun registerUser(registerEntity: RegisterEntity) {
        viewModelScope.launch {
            repository.addUser(registerEntity)
        }
    }
}
