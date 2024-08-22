package com.example.cookmate.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.cookmate.R
import com.example.cookmate.data.local.RoomManager
import com.example.cookmate.data.local.entity.RegisterEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterFragment : Fragment() {

    private lateinit var titleTextView: TextView
    private lateinit var subtitleTextView: TextView
    private lateinit var nameLabelTextView: TextView
    private lateinit var nameEditText: EditText
    private lateinit var emailLabelTextView: TextView
    private lateinit var emailEditText: EditText
    private lateinit var passwordLabelTextView: TextView
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordLabelTextView: TextView
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var termsCheckBox: CheckBox
    private lateinit var signUpButton: Button
    private lateinit var alreadyMemberTextView: TextView
    private lateinit var signInTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        // Initialize views
        titleTextView = view.findViewById(R.id.titleTextView)
        subtitleTextView = view.findViewById(R.id.subtitleTextView)
        nameLabelTextView = view.findViewById(R.id.nameLabelTextView)
        nameEditText = view.findViewById(R.id.nameEditText)
        emailLabelTextView = view.findViewById(R.id.emailLabelTextView)
        emailEditText = view.findViewById(R.id.emailEditText)
        passwordLabelTextView = view.findViewById(R.id.passwordLabelTextView)
        passwordEditText = view.findViewById(R.id.passwordEditText)
        confirmPasswordLabelTextView = view.findViewById(R.id.confirmPasswordLabelTextView)
        confirmPasswordEditText = view.findViewById(R.id.confirmPasswordEditText)
        termsCheckBox = view.findViewById(R.id.termsCheckBox)
        signUpButton = view.findViewById(R.id.signUpButton)
        alreadyMemberTextView = view.findViewById(R.id.already_member)
        signInTextView = view.findViewById(R.id.signInTextView)

        // Setup onClickListeners or other logic
        signUpButton.setOnClickListener {
            handleSignUp()
        }

        signInTextView.setOnClickListener {
            navigateToSignIn()
        }

        return view
    }

    private fun handleSignUp() {
        val name = nameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        val confirmPassword = confirmPasswordEditText.text.toString().trim()
        val termsAccepted = termsCheckBox.isChecked

        if (validateInputs(name, email, password, confirmPassword, termsAccepted)) {
            // Proceed with sign up process
            val registerEntity = RegisterEntity(name = name, email = email, password = password)

            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    val db = RoomManager.getInit(requireContext())
                    db.authDao.addUser(registerEntity)
                }
                // Navigate to login fragment after successful registration
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        } else {
            // Show a message if inputs are not valid
            Toast.makeText(requireContext(), "Please correct the errors", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateInputs(name: String, email: String, password: String, confirmPassword: String, termsAccepted: Boolean): Boolean {
        return when {
            name.isEmpty() -> {
                nameEditText.error = "Name is required"
                false
            }
            email.isEmpty() -> {
                emailEditText.error = "Email is required"
                false
            }
            password.isEmpty() -> {
                passwordEditText.error = "Password is required"
                false
            }
            password != confirmPassword -> {
                confirmPasswordEditText.error = "Passwords do not match"
                false
            }
            !termsAccepted -> {
                termsCheckBox.error = "You must accept our Terms & Conditions"
                false
            }
            else -> true
        }
    }

    private fun navigateToSignIn() {
        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
    }
}
