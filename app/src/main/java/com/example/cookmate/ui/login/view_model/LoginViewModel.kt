package com.example.cookmate.ui.login.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookmate.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository) : ViewModel() {

    private val _loginStatus = MutableLiveData<Boolean>()
    val loginStatus: LiveData<Boolean> get() = _loginStatus

    private val _inputError = MutableLiveData<String?>()
    val inputError: LiveData<String?> get() = _inputError

    fun onLoginClicked(userName: String, password: String) {
        if (userName.trim().isBlank() || password.trim().isBlank()) {
            _inputError.value = "Please enter both name and password."
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            val userExists = repository.getAllUsers().any { it.name == userName && it.password == password }
            _loginStatus.postValue(userExists)
        }
    }

    fun clearInputError() {
        _inputError.value = null
    }
}
