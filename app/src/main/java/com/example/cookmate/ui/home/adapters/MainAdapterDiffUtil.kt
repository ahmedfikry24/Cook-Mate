package com.example.cookmate.ui.home.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.cookmate.ui.home.view_model.CategoryInfo

class MainAdapterDiffUtil(
    private val oldItems: List<CategoryInfo>,
    private val newItems: List<CategoryInfo>,
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
