package com.example.cookmate.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.cookmate.R

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set a delay before navigating to the login fragment
        Handler(Looper.getMainLooper()).postDelayed({
            // Navigate to the LoginFragment
            findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        }, 3000) // Delay for 3 seconds or adjust based on your animation duration
    }
}
