package com.example.cookmate.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.cookmate.R
import com.example.cookmate.ui.home.adapters.HomeAdapter

class HomeFragment : Fragment() {
    private lateinit var mainRecycler: RecyclerView
    private lateinit var mainAdapter: HomeAdapter
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    private fun initViews(view: View) {
        mainRecycler = view.findViewById(R.id.main_recycler)
        mainAdapter = HomeAdapter(listOf())
        mainRecycler.adapter = mainAdapter
        progressBar = view.findViewById(R.id.home_progress_bar)
    }
}