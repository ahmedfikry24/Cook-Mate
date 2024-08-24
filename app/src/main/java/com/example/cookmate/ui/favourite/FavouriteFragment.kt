package com.example.cookmate.ui.favourite

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.cookmate.R
import com.example.cookmate.RecipeActivity
import com.example.cookmate.ui.base.BaseFragment
import com.example.cookmate.ui.favourite.adapter.FavoriteAdapter
import com.example.cookmate.ui.favourite.view_model.FavoriteEvents
import com.example.cookmate.ui.favourite.view_model.FavoriteViewModel

class FavouriteFragment : BaseFragment<FavoriteViewModel>() {

    private lateinit var recycler: RecyclerView
    private lateinit var favoriteAdapter: FavoriteAdapter

    override val fragmentId = R.layout.fragment_favourite
    override val viewModelClass = FavoriteViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getFavoriteRecipes()
    }

    override fun onResume() {
        super.onResume()
        val recipeActivity = activity as RecipeActivity
        recipeActivity.controlNavDrawerVisibility(true)
        recipeActivity.controlBottomNavVisibility(true)
    }

    override fun initViews(view: View) {
        recycler = view.findViewById(R.id.favorite_recycler)
        favoriteAdapter = FavoriteAdapter(listOf(), viewModel)
        recycler.adapter = favoriteAdapter
    }

    override fun viewModelObservers() {
        viewModel.favoriteRecipes.observe(viewLifecycleOwner) { recipes ->
            favoriteAdapter.updateRecipes(recipes)
        }
        viewModel.events.observe(viewLifecycleOwner) { event ->
            when (event) {
                FavoriteEvents.Idle -> Unit
                is FavoriteEvents.OnClickItem -> {
                    val direction =
                        FavouriteFragmentDirections.actionFavouriteFragmentToRecipeDetailsFragment(
                            event.id
                        )
                    navController.navigate(direction)
                }
            }
            viewModel.resetEventsToInitialState()
        }
    }
}