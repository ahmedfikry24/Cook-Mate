package com.example.cookmate.ui.home

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.cookmate.R
import com.example.cookmate.RecipeActivity
import com.example.cookmate.ui.base.BaseFragment
import com.example.cookmate.ui.home.adapters.HomeMainAdapter
import com.example.cookmate.ui.home.view_model.HomeEvents
import com.example.cookmate.ui.home.view_model.HomeViewModel

class HomeFragment : BaseFragment<HomeViewModel>() {

    private lateinit var mainRecycler: RecyclerView
    private lateinit var mainAdapter: HomeMainAdapter
    private lateinit var progressBar: ProgressBar

    override val fragmentId = R.layout.fragment_home
    override val viewModelClass = HomeViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getDate()
    }

    override fun onResume() {
        super.onResume()
        val recipeActivity = activity as RecipeActivity
        recipeActivity.controlNavDrawerVisibility(true)
    }

    override fun initViews(view: View) {
        mainRecycler = view.findViewById(R.id.main_recycler)
        mainAdapter = HomeMainAdapter(listOf(), listOf(), viewModel)
        mainRecycler.adapter = mainAdapter
        progressBar = view.findViewById(R.id.home_progress_bar)
    }

    override fun viewModelObservers() {
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            progressBar.isVisible = categories.isEmpty()
            mainRecycler.isVisible = categories.isNotEmpty()
            mainAdapter.updateCategories(categories)
        }
        viewModel.categoriesRecipes.observe(viewLifecycleOwner) { recipes ->
            mainAdapter.recipesAdapter.updateRecipes(recipes)
        }
        viewModel.recipesOfDay.observe(viewLifecycleOwner) { recipes ->
            mainAdapter.updateRecipesOfDay(recipes)
        }

        viewModel.favoriteRecipes.observe(viewLifecycleOwner) { recipes ->
            mainAdapter.favoriteAdapter.updateRecipes(recipes)
        }
        viewModel.events.observe(viewLifecycleOwner) { event ->
            when (event) {
                HomeEvents.Idle -> Unit
                is HomeEvents.OnClickMeal -> {
                    val direction =
                        HomeFragmentDirections.actionHomeFragmentToRecipeDetailsFragment(event.id)
                    navController.navigate(direction)
                }

                HomeEvents.OnCLickFavoriteMore -> navController.navigate(R.id.favouriteFragment)
            }
            viewModel.resetEventToInitialState()
        }
    }
}