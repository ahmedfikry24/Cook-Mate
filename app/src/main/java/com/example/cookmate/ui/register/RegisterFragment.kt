package com.example.cookmate.ui.register

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.cookmate.R
import com.example.cookmate.ui.base.BaseFragment
import com.example.cookmate.ui.register.view_model.RegisterEvents
import com.example.cookmate.ui.register.view_model.RegisterViewModel

class RegisterFragment : BaseFragment<RegisterViewModel>() {

    private lateinit var nameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var signUpButton: Button
    private lateinit var signInTextView: TextView

    override val fragmentId = R.layout.fragment_register
    override val viewModelClass = RegisterViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    override fun initViews(view: View) {
        nameEditText = view.findViewById(R.id.nameEditText)
        passwordEditText = view.findViewById(R.id.passwordEditText)
        confirmPasswordEditText = view.findViewById(R.id.confirmPasswordEditText)
        signUpButton = view.findViewById(R.id.signUpButton)
        signInTextView = view.findViewById(R.id.signInTextView)
    }

    override fun viewModelObservers() {
        viewModel.events.observe(viewLifecycleOwner) { event ->
            when (event) {
                RegisterEvents.Idle -> Unit
                is RegisterEvents.EmptyRequiredFields -> {
                    Toast.makeText(
                        requireContext(),
                        "Please fill the required fields",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                RegisterEvents.RegisterSSuccess -> {
                    Toast.makeText(
                        requireContext(),
                        "User registered successfully",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    navController.popBackStack()
                }
            }
            viewModel.resetEventsToInitialState()
        }
    }

    private fun initListeners() {
        signUpButton.setOnClickListener {
            viewModel.registerUser(
                nameEditText.text.toString(),
                passwordEditText.text.toString(),
                confirmPasswordEditText.text.toString()
            )
        }
        signInTextView.setOnClickListener { navController.popBackStack() }
    }
}
