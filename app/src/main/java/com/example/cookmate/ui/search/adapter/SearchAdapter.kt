package com.example.cookmate.ui.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cookmate.R
import com.example.cookmate.ui.base.BaseDiffUtil
import com.example.cookmate.ui.search.view_model.SearchInteractions
import com.example.cookmate.ui.shared_ui_state.RecipeUiState
import com.example.cookmate.ui.utils.loadImageUrl

class SearchAdapter(
    private var recipes: List<RecipeUiState>,
    private val interactions: SearchInteractions,
) : RecyclerView.Adapter<SearchAdapter.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val item = recipes[position]
        holder.apply {
            recipeTitle.text = item.name
            recipeImage.loadImageUrl(item.imageUrl)
            itemView.setOnClickListener { interactions.onClickRecipe(item.id) }
        }
    }

    override fun getItemCount() = recipes.size

    inner class RecipeViewHolder(itemView: View) : ViewHolder(itemView) {
        val recipeImage: ImageView = itemView.findViewById(R.id.recipeImage)
        val recipeTitle: TextView = itemView.findViewById(R.id.recipeTitle)
    }

    fun updateResult(newItems: List<RecipeUiState>) {
        val diffUtil = DiffUtil.calculateDiff(
            BaseDiffUtil(
                recipes,
                newItems,
                areItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
                areContentsTheSame = { oldItem, newItem -> oldItem == newItem }
            )
        )
        recipes = newItems
        diffUtil.dispatchUpdatesTo(this)
    }
}
