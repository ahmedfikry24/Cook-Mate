package com.example.cookmate.ui.login

import android.content.Context
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cookmate.R
import com.example.cookmate.data.local.RoomManager
import com.example.cookmate.data.local.entity.RegisterEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {

    private lateinit var nameInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var signInButton: Button
    private lateinit var signUpTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_login, container, false)

        val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            val navController = findNavController()
            navController.setGraph(R.navigation.user_nav_graph)
            navController.navigate(R.id.homeFragment)
            return rootView
        }

        nameInput = rootView.findViewById(R.id.nameInput)
        passwordInput = rootView.findViewById(R.id.passwordInput)
        signInButton = rootView.findViewById(R.id.signInButton)
        signUpTextView = rootView.findViewById(R.id.signUp)

        // For testing purposes, since nothing exists in the database yet
        CoroutineScope(Dispatchers.IO).launch {
            val authDao = RoomManager.getInit(requireContext()).authDao
            authDao.addUser(RegisterEntity(name = "testuser", password = "testpassword", email = "test"))
        }

        // Logic for "Sign Up" text with yellow color and clickable action
        val signUpText = "Don't have an account? Sign Up"
        val spannableString = SpannableString(signUpText)

        // Set the yellow color for "Sign Up"
        val yellowColor = ContextCompat.getColor(requireContext(), R.color.secondary)
        val yellowColorSpan = ForegroundColorSpan(yellowColor)
        spannableString.setSpan(yellowColorSpan, 23, signUpText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Make "Sign Up" clickable
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
        spannableString.setSpan(clickableSpan, 23, signUpText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Apply the SpannableString to the TextView
        signUpTextView.text = spannableString
        signUpTextView.movementMethod = LinkMovementMethod.getInstance() // Enable clicking on the span
        signUpTextView.highlightColor = ContextCompat.getColor(requireContext(), android.R.color.transparent)

        signInButton.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (name.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter both name and password.", Toast.LENGTH_SHORT).show()
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    val authDao = RoomManager.getInit(requireContext()).authDao
                    val userExists = authDao.getAllUsers().any { it.name == name && it.password == password }

                    withContext(Dispatchers.Main) {
                        if (userExists) {
                            sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()

                            // Switch to the user_nav_graph and navigate to HomeFragment
                            val navController = findNavController()
                            navController.setGraph(R.navigation.user_nav_graph)
                            navController.navigate(R.id.homeFragment)
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
