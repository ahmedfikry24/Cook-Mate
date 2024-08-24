package com.example.cookmate.ui.home.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.cookmate.ui.shared_ui_state.RecipeUiState

class HomeRecipeDiffUtil(
    private val oldItems: List<RecipeUiState>,
    private val newItems: List<RecipeUiState>,
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].id == newItems[newItemPosition].id
                && oldItems[oldItemPosition].name == newItems[newItemPosition].name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].id == newItems[newItemPosition].id
                && oldItems[oldItemPosition].name == newItems[newItemPosition].name
    }
}
