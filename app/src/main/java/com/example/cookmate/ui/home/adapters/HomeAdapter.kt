package com.example.cookmate.ui.home.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cookmate.R
import com.example.cookmate.ui.home.view_model.CategoryInfo

class HomeAdapter(
    private var categories: List<CategoryInfo>,
) : RecyclerView.Adapter<HomeAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return when (viewType) {
            FIRST_VIEW -> CategoriesTabsViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.home_categories_tabs, parent, false)
            )

            SECOND_VIEW -> CategoryRecipesViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.home_category_recipes, parent, false)
            )

            else -> RecipeOfDayViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.home_recipe_of_day, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateCategories(categories: List<CategoryInfo>) {
        this.categories = categories
        notifyDataSetChanged()
    }

    override fun getItemCount() = 3
    override fun getItemViewType(position: Int) = position

    abstract class MainViewHolder(view: View) : ViewHolder(view)
    class CategoriesTabsViewHolder(view: View) : MainViewHolder(view)
    class CategoryRecipesViewHolder(view: View) : MainViewHolder(view)
    class RecipeOfDayViewHolder(view: View) : MainViewHolder(view)

    companion object {
        private const val FIRST_VIEW = 0
        private const val SECOND_VIEW = 1
    }
}
