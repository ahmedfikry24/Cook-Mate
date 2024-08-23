package com.example.cookmate.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cookmate.R
import com.example.cookmate.ui.home.view_model.HomeInteractions
import com.example.cookmate.ui.shared_ui_state.RecipeUiState
import com.example.cookmate.ui.utils.loadImageUrl

class HomeRecipesAdapter(
    private var recipes: List<RecipeUiState>,
    private val interactions: HomeInteractions,
) : RecyclerView.Adapter<HomeRecipesAdapter.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.home_recipe_item, parent, false)
        )
    }

    override fun getItemCount() = recipes.size

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val item = recipes[position]
        holder.apply {
            image.loadImageUrl(item.imageUrl)
            text.text = item.name
            holder.itemView.setOnClickListener { interactions.onClickRecipe(item.id) }
        }
    }

    fun updateRecipes(newItems: List<RecipeUiState>) {
        val diffUtil = DiffUtil.calculateDiff(HomeRecipeDiffUtil(recipes, newItems))
        recipes = newItems
        diffUtil.dispatchUpdatesTo(this)
    }

    class RecipeViewHolder(view: View) : ViewHolder(view) {
        val image: ImageFilterView = view.findViewById(R.id.image_recipe)
        val text: TextView = view.findViewById(R.id.text_recipe)
    }
}