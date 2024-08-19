package com.example.cookmate.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cookmate.R
import com.example.cookmate.data.model.MealDto
import com.example.cookmate.data.remote.RetrofitManager
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: androidx.appcompat.widget.SearchView

    private var recipeList = listOf<MealDto.Recipe>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                val response = RetrofitManager.service.recipeSearch(query)
                if (response.isSuccessful) {
                    recipeList = response.body()?.meals?.filterNotNull().orEmpty()
                    (recyclerView.adapter as RecipeAdapter).updateData(recipeList)
                    if (recipeList.isEmpty()) {
                        Toast.makeText(requireContext(), "No recipes found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Error fetching data", Toast.LENGTH_SHORT).show()
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
            // Create the item layout programmatically
            val itemLayout = ConstraintLayout(parent.context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                setPadding(8, 8, 8, 8)

                // ImageView for recipe image
                val imageView = ImageView(context).apply {
                    id = View.generateViewId()
                    layoutParams = ConstraintLayout.LayoutParams(0, 0).apply {
                        dimensionRatio = "1:1"
                    }
                    scaleType = ImageView.ScaleType.CENTER_CROP
                }

                // TextView for recipe title
                val textView = TextView(context).apply {
                    id = View.generateViewId()
                    layoutParams = ConstraintLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                    setTextColor(context.getColor(R.color.text))
                }

                addView(imageView)
                addView(textView)

                // Set constraints programmatically using ConstraintSet
                val constraintSet = ConstraintSet()
                constraintSet.clone(this)
                constraintSet.connect(imageView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
                constraintSet.connect(imageView.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
                constraintSet.connect(imageView.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
                constraintSet.connect(textView.id, ConstraintSet.TOP, imageView.id, ConstraintSet.BOTTOM)
                constraintSet.connect(textView.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
                constraintSet.connect(textView.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
                constraintSet.applyTo(this)
            }

            return RecipeViewHolder(itemLayout)
        }

        override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
            val recipe = data[position]
            holder.bind(recipe)
        }

        override fun getItemCount() = data.size

        inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            // Directly reference the views using the IDs
            private val recipeImage: ImageView = itemView.findViewById(itemView.findViewById<ImageView>(View.generateViewId()).id)
            private val recipeTitle: TextView = itemView.findViewById(itemView.findViewById<TextView>(View.generateViewId()).id)

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
