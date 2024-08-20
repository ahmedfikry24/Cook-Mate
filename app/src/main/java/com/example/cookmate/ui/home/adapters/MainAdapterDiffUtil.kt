package com.example.cookmate.ui.home.adapters

import androidx.recyclerview.widget.DiffUtil

class MainAdapterDiffUtil<T>(
    private val oldItems: List<T>,
    private val newItems: List<T>,
    private val areItemsTheSame: (oldItem: T, newItem: T) -> Boolean,
    private val areContentsTheSame: (oldItem: T, newItem: T) -> Boolean,
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldItems.size
    override fun getNewListSize() = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areItemsTheSame(oldItems[oldItemPosition], newItems[newItemPosition])

    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areContentsTheSame(oldItems[oldItemPosition], newItems[newItemPosition])
    }
}
