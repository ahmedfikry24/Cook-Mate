package com.example.cookmate.ui.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cookmate.R
import com.example.cookmate.data.local.RoomManager
import com.example.cookmate.data.remote.RetrofitManager
import com.example.cookmate.data.repository.RepositoryImpl
import com.example.cookmate.data.source.LocalDataSourceImpl
import com.example.cookmate.data.source.RemoteDataSourceImpl
import com.example.cookmate.ui.favourite.view_model.FavoriteViewModel
import com.example.cookmate.ui.favourite.view_model.FavoriteViewModelFactory

class FavouriteFragment : Fragment() {
    private val remoteDataSource by lazy { RemoteDataSourceImpl(RetrofitManager.service) }
    private val localDataSource by lazy { LocalDataSourceImpl(RoomManager.getInit(requireContext())) }
    private val repository by lazy { RepositoryImpl(remoteDataSource, localDataSource) }
    private val viewModel by viewModels<FavoriteViewModel> { FavoriteViewModelFactory(repository) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }
}