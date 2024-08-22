package com.example.cookmate.ui.search

import androidx.recyclerview.widget.DiffUtil
import com.example.cookmate.data.model.MealDto

class RecipeDiffUtil(
    private val oldItems: List<MealDto.Recipe>,
    private val newItems: List<MealDto.Recipe>,
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldItems.size
    override fun getNewListSize() = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].id == newItems[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] == newItems[newItemPosition]
    }
}
