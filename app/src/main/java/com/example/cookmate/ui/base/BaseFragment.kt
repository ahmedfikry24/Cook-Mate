package com.example.cookmate.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cookmate.data.local.RoomManager
import com.example.cookmate.data.remote.RetrofitManager
import com.example.cookmate.data.repository.RepositoryImpl
import com.example.cookmate.data.source.LocalDataSourceImpl
import com.example.cookmate.data.source.RemoteDataSourceImpl

abstract class BaseFragment<V> : Fragment() {

    private val remoteDataSource by lazy { RemoteDataSourceImpl(RetrofitManager.service) }
    private val localDataSource by lazy { LocalDataSourceImpl(RoomManager.getInit(requireContext())) }
    private val repository by lazy { RepositoryImpl(remoteDataSource, localDataSource) }
    protected val viewModel: V by viewModels { BaseViewModelFactory(repository) }
    protected val navController by lazy { findNavController() }

    abstract val fragmentId: Int

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(fragmentId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        viewModelObservers()
    }


    abstract fun initViews(view: View)
    abstract fun viewModelObservers()

}
