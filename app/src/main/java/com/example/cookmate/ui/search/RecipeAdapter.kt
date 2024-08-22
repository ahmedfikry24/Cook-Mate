package com.example.cookmate.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cookmate.R
import com.example.cookmate.data.model.MealDto

class RecipeAdapter(
    private val onRecipeClick: (String) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    private var recipes = listOf<MealDto.Recipe>()

    fun updateData(newData: List<MealDto.Recipe>) {
        val diffUtil = DiffUtil.calculateDiff(RecipeDiffUtil(recipes, newData))
        recipes = newData
        diffUtil.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.bind(recipe)
    }

    override fun getItemCount() = recipes.size

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val recipeImage: ImageView = itemView.findViewById(R.id.recipeImage)
        private val recipeTitle: TextView = itemView.findViewById(R.id.recipeTitle)

        fun bind(recipe: MealDto.Recipe) {
            recipeTitle.text = recipe.name
            Glide.with(itemView.context)
                .load(recipe.imageUrl)
                .into(recipeImage)

            itemView.setOnClickListener { onRecipeClick(recipe.id!!) }
        }
    }
}
