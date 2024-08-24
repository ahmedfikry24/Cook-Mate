package com.example.cookmate.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cookmate.R
import com.example.cookmate.data.local.entity.RegisterEntity
import com.example.cookmate.data.repository.Repository

class RegisterFragment : Fragment() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var signUpButton: Button
    private lateinit var signInTextView: TextView

    private lateinit var repository: Repository

    private val viewModel: RegisterViewModel by viewModels {
        RegisterViewModelFactory(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        nameEditText = view.findViewById(R.id.nameEditText)
        emailEditText = view.findViewById(R.id.emailEditText)
        passwordEditText = view.findViewById(R.id.passwordEditText)
        confirmPasswordEditText = view.findViewById(R.id.confirmPasswordEditText)
        signUpButton = view.findViewById(R.id.signUpButton)
        signInTextView = view.findViewById(R.id.signInTextView)

        signUpButton.setOnClickListener {
            handleSignUp()
        }

        signInTextView.setOnClickListener {
            findNavController().popBackStack()
        }

        return view
    }

    private fun handleSignUp() {
        val name = nameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        val confirmPassword = confirmPasswordEditText.text.toString().trim()

        if (validateInputs(name, email, password, confirmPassword)) {
            viewModel.registerUser(RegisterEntity(id, name, email, password))
            Toast.makeText(requireContext(), "User registered successfully", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        } else {
            Toast.makeText(requireContext(), "Please fill the required fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateInputs(name: String, email: String, password: String, confirmPassword: String): Boolean {
        return when {
            name.isBlank() -> {
                nameEditText.error = "Name is required"
                false
            }
            email.isBlank() -> {
                emailEditText.error = "Email is required"
                false
            }
            password.isBlank() -> {
                passwordEditText.error = "Password is required"
                false
            }
            password != confirmPassword -> {
                confirmPasswordEditText.error = "Passwords do not match"
                false
            }
            else -> true
        }
    }
}
