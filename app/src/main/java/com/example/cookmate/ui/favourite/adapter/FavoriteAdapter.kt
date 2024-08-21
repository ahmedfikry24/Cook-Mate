package com.example.cookmate.ui.favourite.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.cookmate.R
import com.example.cookmate.ui.favourite.view_model.FavoriteRecipeInfo

class FavoriteAdapter(
    private var recipes: List<FavoriteRecipeInfo>,
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.favorite_recipe_item, parent, false)
        )
    }

    override fun getItemCount() = recipes.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val item = recipes[position]
        holder.apply {
            image.loadUrl(item.url)
            title.text = item.name
            area.text = item.area
            category.text = item.category
            icon.setOnClickListener { item.onClickFavorite(item.id) }
            image.setOnClickListener { item.onClickItem(item.id) }
        }
    }

    private fun AppCompatImageView.loadUrl(url: String) {
        Glide.with(this).load(url).into(this)
    }

    fun updateRecipes(newItems: List<FavoriteRecipeInfo>) {
        val diffUtil = DiffUtil.calculateDiff(FavoriteDiffUtil(recipes, newItems))
        recipes = newItems
        diffUtil.dispatchUpdatesTo(this)
    }

    class FavoriteViewHolder(view: View) : ViewHolder(view) {
        val image: AppCompatImageView = view.findViewById(R.id.image_favorite)
        val title: TextView = view.findViewById(R.id.title_favorite)
        val area: TextView = view.findViewById(R.id.area_favorite)
        val category: TextView = view.findViewById(R.id.category_favorite)
        val icon: AppCompatImageView = view.findViewById(R.id.favorite_icon)
    }
}
