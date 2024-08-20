package com.example.cookmate.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.cookmate.R
import com.example.cookmate.data.local.RoomManager
import com.example.cookmate.data.remote.RetrofitManager
import com.example.cookmate.data.repository.RepositoryImpl
import com.example.cookmate.data.source.LocalDataSourceImpl
import com.example.cookmate.data.source.RemoteDataSourceImpl
import com.example.cookmate.ui.home.adapters.MainAdapter
import com.example.cookmate.ui.home.view_model.HomeEvents
import com.example.cookmate.ui.home.view_model.HomeViewModel
import com.example.cookmate.ui.home.view_model.HomeViewModelFactory

class HomeFragment : Fragment() {
    private lateinit var mainRecycler: RecyclerView
    private lateinit var mainAdapter: MainAdapter
    private lateinit var progressBar: ProgressBar

    private val remoteDataSource by lazy { RemoteDataSourceImpl(RetrofitManager.service) }
    private val localDataSource by lazy { LocalDataSourceImpl(RoomManager.getInit(requireContext())) }
    private val repository by lazy { RepositoryImpl(remoteDataSource, localDataSource) }
    private val viewModel by viewModels<HomeViewModel> { HomeViewModelFactory(repository) }

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCategories()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        viewModelObservers()
    }

    private fun initViews(view: View) {
        mainRecycler = view.findViewById(R.id.main_recycler)
        mainAdapter = MainAdapter(listOf())
        mainRecycler.adapter = mainAdapter
        progressBar = view.findViewById(R.id.home_progress_bar)
        navController = findNavController()
    }

    private fun viewModelObservers() {
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            progressBar.isVisible = categories.isEmpty()
            mainRecycler.isVisible = categories.isNotEmpty()
            mainAdapter.updateCategories(categories)
        }
        viewModel.meals.observe(viewLifecycleOwner) { recipes ->
            mainAdapter.recipesAdapter.updateRecipes(recipes)
        }

        viewModel.events.observe(viewLifecycleOwner) { event ->
            when (event) {
                HomeEvents.Idle -> Unit
                is HomeEvents.OnClickCategory -> viewModel.getMealsByCategory(event.name)
                is HomeEvents.OnClickMeal -> {
                    val direction =
                        HomeFragmentDirections.actionHomeFragmentToRecipeDetailsFragment(event.id)
                    navController.navigate(direction)
                }
            }
            viewModel.events.postValue(HomeEvents.Idle)
        }
    }
}