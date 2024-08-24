package com.example.cookmate.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.cookmate.AuthActivity
import com.example.cookmate.R
import com.example.cookmate.data.local.shared_pref.SharedPrefManager
import com.example.cookmate.ui.base.BaseFragment
import com.example.cookmate.ui.login.view_model.LoginViewModel

class LoginFragment : BaseFragment<LoginViewModel>() {

    private lateinit var nameInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var signInButton: Button
    private lateinit var signUpTextView: TextView

    override val fragmentId = R.layout.fragment_login
    override val viewModelClass = LoginViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    override fun initViews(view: View) {
        nameInput = view.findViewById(R.id.nameInput)
        passwordInput = view.findViewById(R.id.passwordInput)
        signInButton = view.findViewById(R.id.signInButton)
        signUpTextView = view.findViewById(R.id.signUp)
    }

    override fun viewModelObservers() {
        viewModel.inputError.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                showToast(it)
                viewModel.clearInputError()
            }
        }

        viewModel.loginStatus.observe(viewLifecycleOwner) { userExists ->
            if (userExists) {
                SharedPrefManager.isLogin = true
                navigateToHome()
            } else {
                showToast("Wrong name or password, please try again.")
            }
        }
    }

    private fun initListeners() {
        signInButton.setOnClickListener {
            viewModel.onLoginClicked(nameInput.text.toString(), passwordInput.text.toString())
        }

        signUpTextView.setOnClickListener { findNavController().navigate(R.id.action_loginFragment_to_registerFragment) }
    }

    private fun navigateToHome() {
        val recipeActivity = activity as AuthActivity
        recipeActivity.navigateToRecipeActivity()
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
