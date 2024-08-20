package com.example.cookmate.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cookmate.R
import com.example.cookmate.data.local.entity.FavouriteRecipeEntity
import com.example.cookmate.data.local.entity.RegisterEntity
import com.example.cookmate.data.model.MealDto
import com.example.cookmate.data.repository.RepositoryImpl
import com.example.cookmate.data.source.LocalDataSource
import com.example.cookmate.data.source.RemoteDataSourceImpl
import kotlinx.coroutines.launch
import com.example.cookmate.data.remote.RetrofitManager

class SearchFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: androidx.appcompat.widget.SearchView
    private lateinit var repository: RepositoryImpl

    private var recipeList = listOf<MealDto.Recipe>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Create a dummy LocalDataSource
        val dummyLocalDataSource = object : LocalDataSource {
            override suspend fun addUser(user: RegisterEntity) { /* No-op */ }
            override suspend fun getAllUsers(): List<RegisterEntity> = emptyList()
            override suspend fun addFavouriteRecipe(recipe: FavouriteRecipeEntity) { /* No-op */ }
            override suspend fun getAllFavouriteRecipes(): List<FavouriteRecipeEntity> = emptyList()
            override suspend fun removeFavouriteRecipe(recipeId: Int) { /* No-op */ }
        }

        // Initialize the repository with the dummy LocalDataSource and RemoteDataSourceImpl
        repository = RepositoryImpl(
            localDataSource = dummyLocalDataSource,
            remoteDataSource = RemoteDataSourceImpl(RetrofitManager.service)
        )

        recyclerView = view.findViewById(R.id.recipesRecyclerView)
        searchView = view.findViewById(R.id.searchView)

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = RecipeAdapter()

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    searchRecipes(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun searchRecipes(query: String) {
        lifecycleScope.launch {
            try {
                val recipes = repository.recipeSearch(query)
                recipeList = recipes
                (recyclerView.adapter as RecipeAdapter).updateData(recipeList)
                if (recipeList.isEmpty()) {
                    Toast.makeText(requireContext(), "No recipes found", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error fetching data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    inner class RecipeAdapter : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

        private var data = listOf<MealDto.Recipe>()

        fun updateData(newData: List<MealDto.Recipe>) {
            data = newData
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
            // Inflate the item layout
            val itemLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recipe, parent, false)

            return RecipeViewHolder(itemLayout)
        }

        override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
            val recipe = data[position]
            holder.bind(recipe)
        }

        override fun getItemCount() = data.size

        inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            private val recipeImage: ImageView = itemView.findViewById(R.id.recipeImage)
            private val recipeTitle: TextView = itemView.findViewById(R.id.recipeTitle)

            fun bind(recipe: MealDto.Recipe) {
                recipeTitle.text = recipe.name
                Glide.with(itemView.context)
                    .load(recipe.imageUrl)
                    .into(recipeImage)

                itemView.setOnClickListener {
                    val action = SearchFragmentDirections
                        .actionSearchFragmentToRecipeDetailsFragment(recipe.id!!)
                    findNavController().navigate(action)
                }
            }
        }
    }
}
