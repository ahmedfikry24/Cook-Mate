package com.example.cookmate.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import com.example.cookmate.R
import com.example.cookmate.data.remote.RetrofitManager
import com.example.cookmate.data.repository.RepositoryImpl
import com.example.cookmate.data.source.RemoteDataSourceImpl

class SearchFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView

    private val remoteDataSource by lazy { RemoteDataSourceImpl(RetrofitManager.service) }
    private val repository by lazy { RepositoryImpl(remoteDataSource) }
    private val viewModel by viewModels<SearchViewModel> { SearchViewModelFactory(repository) }

    private val recipeAdapter by lazy { RecipeAdapter { recipeId ->
        val action = SearchFragmentDirections.actionSearchFragmentToRecipeDetailsFragment(recipeId)
        findNavController().navigate(action)
    } }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        observeViewModel()
    }

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recipesRecyclerView)
        searchView = view.findViewById(R.id.searchView)

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = recipeAdapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    viewModel.searchRecipes(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
    }

    private fun observeViewModel() {
        viewModel.recipes.observe(viewLifecycleOwner) { recipes ->
            recipeAdapter.updateData(recipes)
            if (recipes.isEmpty()) {
                Toast.makeText(requireContext(), "No recipes found", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                viewModel.clearErrorMessage()
            }
        }
    }
}
