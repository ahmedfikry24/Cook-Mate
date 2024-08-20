package com.example.cookmate.ui.favourite.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.cookmate.ui.favourite.view_model.FavoriteRecipeInfo

class FavoriteDiffUtil(
    private val oldItems: List<FavoriteRecipeInfo>,
    private val newItems: List<FavoriteRecipeInfo>,
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldItems.size
    override fun getNewListSize() = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].id == newItems[oldItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] == newItems[oldItemPosition]
    }
}
