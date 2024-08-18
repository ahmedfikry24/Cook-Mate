package com.example.cookmate.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cookmate.R
import com.example.cookmate.data.local.RoomManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {

    private lateinit var nameInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var signInButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_login, container, false)

        // Check if the user is already logged in
        val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            return rootView
        }

        // Initialize the views
        nameInput = rootView.findViewById(R.id.nameInput)
        passwordInput = rootView.findViewById(R.id.passwordInput)
        signInButton = rootView.findViewById(R.id.signInButton)

        signInButton.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (name.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter both name and password.", Toast.LENGTH_SHORT).show()
            } else {
                // Validate credentials with the database
                CoroutineScope(Dispatchers.IO).launch {
                    val authDao = RoomManager.getInit(requireContext()).authDao
                    val userExists = authDao.getAllUsers().any { it.name == name && it.password == password }

                    withContext(Dispatchers.Main) {
                        if (userExists) {
                            // Save the login state
                            sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()

                            // Navigate to the HomeFragment
                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        } else {
                            Toast.makeText(requireContext(), "Wrong name or password, please try again.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        return rootView
    }
}
