package com.example.cookmate.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cookmate.R
import com.example.cookmate.ui.home.view_model.HomeInteractions
import com.example.cookmate.ui.home.view_model.RecipeInfo
import com.example.cookmate.ui.utils.loadImageUrl

class HomeFavoriteAdapter(
    var recipes: List<RecipeInfo>,
    private val interactions: HomeInteractions
) : RecyclerView.Adapter<HomeFavoriteAdapter.HomeFavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFavoriteViewHolder {
        return HomeFavoriteViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.home_favorite_item, parent, false)
        )
    }

    override fun getItemCount() = recipes.size

    override fun onBindViewHolder(holder: HomeFavoriteViewHolder, position: Int) {
        val item = recipes[position]
        holder.apply {
            image.loadImageUrl(item.url)
            text.text = item.name
            itemView.setOnClickListener { interactions.onClickRecipe(item.id) }
        }
    }

    fun updateRecipes(newItems: List<RecipeInfo>) {
        val diffUtil = DiffUtil.calculateDiff(
            MainDiffUtil(
                recipes,
                newItems,
                areItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
                areContentsTheSame = { oldItem, newItem -> oldItem == newItem }
            ))
        recipes = newItems.take(5)
        diffUtil.dispatchUpdatesTo(this)
    }

    class HomeFavoriteViewHolder(view: View) : ViewHolder(view) {
        val image: AppCompatImageView = view.findViewById(R.id.image_recipe)
        val text: TextView = view.findViewById(R.id.title_recipe)
    }

}
