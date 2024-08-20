package com.example.cookmate.ui.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.cookmate.ui.favourite.adapter.FavoriteAdapter
import com.example.cookmate.ui.favourite.view_model.FavoriteEvents
import com.example.cookmate.ui.favourite.view_model.FavoriteViewModel
import com.example.cookmate.ui.favourite.view_model.FavoriteViewModelFactory

class FavouriteFragment : Fragment() {
    private lateinit var recycler: RecyclerView
    private lateinit var favoriteAdapter: FavoriteAdapter

    private val remoteDataSource by lazy { RemoteDataSourceImpl(RetrofitManager.service) }
    private val localDataSource by lazy { LocalDataSourceImpl(RoomManager.getInit(requireContext())) }
    private val repository by lazy { RepositoryImpl(remoteDataSource, localDataSource) }
    private val viewModel by viewModels<FavoriteViewModel> { FavoriteViewModelFactory(repository) }

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getFavoriteRecipes()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        viewModelObserver()
    }

    private fun initViews(view: View) {
        recycler = view.findViewById(R.id.favorite_recycler)
        favoriteAdapter = FavoriteAdapter(listOf())
        recycler.adapter = favoriteAdapter
        navController = findNavController()
    }

    private fun viewModelObserver() {
        viewModel.favoriteRecipes.observe(viewLifecycleOwner) { recipes ->
            favoriteAdapter.updateRecipes(recipes)
        }
        viewModel.events.observe(viewLifecycleOwner) { event ->
            when (event) {
                FavoriteEvents.Idle -> Unit
                is FavoriteEvents.OnClickFavorite -> viewModel.removeFavoriteRecipe(event.id)
                is FavoriteEvents.OnClickItem -> {
                    val direction =
                        FavouriteFragmentDirections.actionFavouriteFragmentToRecipeDetailsFragment(
                            event.id
                        )
                    navController.navigate(direction)
                }
            }
            viewModel.events.postValue(FavoriteEvents.Idle)
        }
    }
}