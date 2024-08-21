package com.example.cookmate.ui.login

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.TextPaint
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
//import com.example.cookmate.data.local.entity.RegisterEntity
import com.example.cookmate.data.local.shared_pref.SharedPrefManager
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

        initViews(rootView)
        setupSignUpText()
        handleAutoLogin()

        signInButton.setOnClickListener { handleLogin() }

        return rootView
    }

    private fun initViews(rootView: View) {
        nameInput = rootView.findViewById(R.id.nameInput)
        passwordInput = rootView.findViewById(R.id.passwordInput)
        signInButton = rootView.findViewById(R.id.signInButton)
        signUpTextView = rootView.findViewById(R.id.signUp)
    }

    private fun setupSignUpText() {
        val signUpText = "Don't have an account? Sign Up"
        val spannableString = SpannableString(signUpText)
        val yellowColor = ContextCompat.getColor(requireContext(), R.color.secondary)

        spannableString.setSpan(
            ForegroundColorSpan(yellowColor),
            23, signUpText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableString.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = yellowColor
                ds.isUnderlineText = true
            }
        }, 23, signUpText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        signUpTextView.text = spannableString
        signUpTextView.movementMethod = LinkMovementMethod.getInstance()
        signUpTextView.highlightColor = ContextCompat.getColor(requireContext(), android.R.color.transparent)
    }

    private fun handleAutoLogin() {
        SharedPrefManager.init(requireContext())
        if (SharedPrefManager.isLogin) {
            navigateToHome()
        }
    }

    private fun handleLogin() {
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
                        SharedPrefManager.isLogin = true
                        navigateToHome()
                    } else {
                        Toast.makeText(requireContext(), "Wrong name or password, please try again.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun navigateToHome() {
        val navController = findNavController()
        navController.setGraph(R.navigation.user_nav_graph)
        navController.navigate(R.id.homeFragment)
    }
}
