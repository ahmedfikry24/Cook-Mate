package com.example.cookmate.ui.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookmate.R
import com.example.cookmate.ui.base.BaseFragment
import com.example.cookmate.ui.search.adapter.SearchAdapter
import com.example.cookmate.ui.search.view_model.SearchViewModel

class SearchFragment : BaseFragment<SearchViewModel>() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var searchAdapter: SearchAdapter

    override val fragmentId = R.layout.fragment_search

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    override fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recipesRecyclerView)
        searchView = view.findViewById(R.id.searchView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        searchAdapter = SearchAdapter(listOf(), viewModel)
        recyclerView.adapter = searchAdapter
    }

    override fun viewModelObservers() {
        viewModel.recipes.observe(viewLifecycleOwner) { recipes ->
            searchAdapter.updateResult(recipes)
            if (recipes.isEmpty()) {
                viewModel.notifyUser("No recipes found")
            }
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                viewModel.notifyUser(it)
            }
        }
        viewModel.uiEvent.observe(viewLifecycleOwner) { event ->
            event?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                viewModel.clearUiEvent()
            }
        }
    }

    private fun initListeners() {
        searchView.setOnClickListener {
            searchView.isIconified = false
            viewModel.onSearchViewClicked(searchView.query.toString())
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.onSearchViewClicked(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
    }
}
