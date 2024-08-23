package com.example.cookmate.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookmate.data.repository.Repository
import com.example.cookmate.data.local.entity.RegisterEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository) : ViewModel() {

    // LiveData for the username and password input
    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    // LiveData to observe login status
    private val _loginStatus = MutableLiveData<Boolean>()
    val loginStatus: LiveData<Boolean> get() = _loginStatus

    // LiveData to observe validation errors (e.g., empty fields)
    private val _inputError = MutableLiveData<String?>()
    val inputError: LiveData<String?> get() = _inputError

    // Initialize a test user
    init {
        addTestUser()
    }

    private fun addTestUser() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(RegisterEntity(name = "testuser", password = "testpassword", email = "test@example.com"))
        }
    }

    fun onLoginClicked(inputName: String, inputPassword: String) {
        // Update the username and password LiveData with the passed values
        username.value = inputName
        password.value = inputPassword

        // Validate input fields
        val name = username.value?.trim()
        val pass = password.value?.trim()

        if (name.isNullOrEmpty() || pass.isNullOrEmpty()) {
            _inputError.value = "Please enter both name and password."
            return
        }

        // Proceed with the login process
        viewModelScope.launch(Dispatchers.IO) {
            val userExists = repository.getAllUsers().any { it.name == name && it.password == pass }
            _loginStatus.postValue(userExists)
        }
    }

    fun clearInputError() {
        _inputError.value = null
    }
}
