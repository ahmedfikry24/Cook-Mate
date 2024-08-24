package com.example.cookmate.ui.register.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookmate.data.local.entity.RegisterEntity
import com.example.cookmate.data.repository.Repository
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: Repository) : ViewModel() {

    private val _events = MutableLiveData<RegisterEvents>(RegisterEvents.Idle)
    val events: LiveData<RegisterEvents> = _events

    fun registerUser(userName: String, password: String, rePassword: String) {
        if (validateInputs(userName, password, rePassword)) {
            viewModelScope.launch {
                repository.addUser(
                    RegisterEntity(
                        name = userName,
                        password = password,
                        email = ""
                    )
                )
                _events.postValue(RegisterEvents.RegisterSSuccess)
            }
        }
    }


    private fun validateInputs(name: String, password: String, confirmPassword: String): Boolean {
        return if (name.isBlank() || password.isBlank()) {
            _events.postValue(RegisterEvents.EmptyRequiredFields("require fields"))
            false
        } else if (password != confirmPassword) {
            _events.postValue(RegisterEvents.EmptyRequiredFields("password doesn't match"))
            false
        } else true
    }

    fun resetEventsToInitialState() {
        _events.postValue(RegisterEvents.Idle)
    }
}
