package com.example.cookmate.ui.register.view_model

sealed class RegisterEvents {

    data object Idle : RegisterEvents()
    data class EmptyRequiredFields(val message: String) : RegisterEvents()
    data object RegisterSSuccess : RegisterEvents()
}
