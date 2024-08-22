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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cookmate.R
import com.example.cookmate.data.local.RoomManager
import com.example.cookmate.data.remote.RetrofitManager
import com.example.cookmate.data.repository.RepositoryImpl
import com.example.cookmate.data.source.LocalDataSourceImpl
import com.example.cookmate.data.source.RemoteDataSourceImpl

class LoginFragment : Fragment() {

    private lateinit var nameInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var signInButton: Button
    private lateinit var signUpTextView: TextView

    private val localDataSource by lazy { LocalDataSourceImpl(RoomManager.getInit(requireContext())) }
    private val remoteDataSource by lazy { RemoteDataSourceImpl(RetrofitManager.service) }
    private val repository by lazy { RepositoryImpl(remoteDataSource, localDataSource) }
    private val viewModel by viewModels<LoginViewModel> { LoginViewModelFactory(repository) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_login, container, false)
        initViews(rootView)
        setupSignUpText()
        observeViewModel()
        signInButton.setOnClickListener { viewModel.onLoginClicked() }
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

    private fun observeViewModel() {
        viewModel.username.observe(viewLifecycleOwner) { nameInput.setText(it) }
        viewModel.password.observe(viewLifecycleOwner) { passwordInput.setText(it) }

        viewModel.inputError.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                viewModel.clearInputError() // Clear error after displaying it
            }
        }

        viewModel.loginStatus.observe(viewLifecycleOwner) { userExists ->
            if (userExists) {
                SharedPrefManager.isLogin = true
                navigateToHome()
            } else {
                Toast.makeText(requireContext(), "Wrong name or password, please try again.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToHome() {
        val navController = findNavController()
        navController.setGraph(R.navigation.user_nav_graph)
        navController.navigate(R.id.homeFragment)
    }
}
