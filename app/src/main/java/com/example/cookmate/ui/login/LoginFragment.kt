package com.example.cookmate.ui.login

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
import com.example.cookmate.data.local.RoomManager
import com.example.cookmate.data.local.shared_pref.SharedPrefManager
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
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        setupSignUpText()
        observeViewModel()

        signInButton.setOnClickListener {
            viewModel.onLoginClicked(
                nameInput.text.toString(),
                passwordInput.text.toString()
            )
        }

        // Set the click listener for "Sign Up" TextView
        signUpTextView.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun initViews(rootView: View) {
        nameInput = rootView.findViewById(R.id.nameInput)
        passwordInput = rootView.findViewById(R.id.passwordInput)
        signInButton = rootView.findViewById(R.id.signInButton)
        signUpTextView = rootView.findViewById(R.id.signUp)
    }

    private fun setupSignUpText() {
        // No additional setup needed for the "Sign Up" text since it is configured in XML.
    }

    private fun observeViewModel() {
        viewModel.inputError.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                viewModel.clearInputError()
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
