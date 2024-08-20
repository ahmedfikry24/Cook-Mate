package com.example.cookmate.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.cookmate.R
import com.example.cookmate.ui.home.view_model.RecipeInfo

class RecipesAdapter(
    private var recipes: List<RecipeInfo>,
) : RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.home_recipe_item, parent, false)
        )
    }

    override fun getItemCount() = recipes.size

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val item = recipes[position]
        holder.apply {
            image.loadUrl(item.url)
            text.text = item.name
        }
    }

    fun updateRecipes(newItems: List<RecipeInfo>) {
        val diffUtil = DiffUtil.calculateDiff(RecipeDiffUtil(recipes, newItems))
        recipes = newItems
        diffUtil.dispatchUpdatesTo(this)
    }

    private fun AppCompatImageView.loadUrl(url: String) {
        Glide.with(this).load(url).into(this)
    }

    class RecipeViewHolder(view: View) : ViewHolder(view) {
        val image: ImageFilterView = view.findViewById(R.id.image_recipe)
        val text: TextView = view.findViewById(R.id.text_recipe)
    }
}