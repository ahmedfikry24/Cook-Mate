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

class SearchFragment : BaseFragment<SearchViewModel>() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView

    override val fragmentId = R.layout.fragment_search

    private val recipeAdapter by lazy {
        RecipeAdapter { recipeId ->
            val action =
                SearchFragmentDirections.actionSearchFragmentToRecipeDetailsFragment(recipeId)
            findNavController().navigate(action)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    override fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recipesRecyclerView)
        searchView = view.findViewById(R.id.searchView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = recipeAdapter
    }

    override fun viewModelObservers() {
        viewModel.recipes.observe(viewLifecycleOwner) { recipes ->
            recipeAdapter.updateData(recipes)
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
