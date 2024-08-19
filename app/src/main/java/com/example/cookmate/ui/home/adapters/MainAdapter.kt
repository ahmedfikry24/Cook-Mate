package com.example.cookmate.ui.home.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cookmate.R
import com.example.cookmate.ui.home.view_model.CategoryInfo
import com.google.android.material.tabs.TabLayout

class MainAdapter(
    private var categories: List<CategoryInfo>,
) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

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
        when (holder) {
            is CategoriesTabsViewHolder -> onBindCategoriesTabs(holder)
        }
    }

    @SuppressLint("InflateParams")
    private fun onBindCategoriesTabs(holder: CategoriesTabsViewHolder) {
        holder.apply {
            categories.forEachIndexed { index, category ->
                if (index == 0) {
                    categories[index].onClick(category.name)
                }
                val tab = tabLayout.newTab()
                val customView =
                    LayoutInflater.from(holder.itemView.context)
                        .inflate(R.layout.custom_tab_view, null)
                val tabText = customView.findViewById<TextView>(R.id.tab_text)
                tabText.text = category.name
                tab.customView = customView
                tab.customView?.let {
                    it.setBackgroundResource(R.drawable.unselected_tab_shape)
                    val text = it.findViewById<TextView>(R.id.tab_text)
                    text.setTextColor(text.context.getColor(R.color.primary))
                }
                tabLayout.addTab(tab)
            }
            tabLayout.getTabAt(0)?.customView?.let {
                it.setBackgroundResource(R.drawable.selected_tab_shape)
                val text = it.findViewById<TextView>(R.id.tab_text)
                text.setTextColor(text.context.getColor(R.color.background))
            }

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.customView?.let {
                        it.setBackgroundResource(R.drawable.selected_tab_shape)
                        val text = it.findViewById<TextView>(R.id.tab_text)
                        text.setTextColor(text.context.getColor(R.color.background))
                    }
                    categories[tab?.position ?: 0].onClick(categories[tab?.position ?: 0].name)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    tab?.customView?.let {
                        it.setBackgroundResource(R.drawable.unselected_tab_shape)
                        val text = it.findViewById<TextView>(R.id.tab_text)
                        text.setTextColor(text.context.getColor(R.color.primary))
                    }
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateCategories(categories: List<CategoryInfo>) {
        this.categories = categories
        notifyDataSetChanged()
    }

    override fun getItemCount() = 3
    override fun getItemViewType(position: Int) = position

    abstract class MainViewHolder(view: View) : ViewHolder(view)
    class CategoriesTabsViewHolder(view: View) : MainViewHolder(view) {
        val tabLayout: TabLayout = view.findViewById(R.id.tab_layout)
    }

    class CategoryRecipesViewHolder(view: View) : MainViewHolder(view)
    class RecipeOfDayViewHolder(view: View) : MainViewHolder(view)

    companion object {
        private const val FIRST_VIEW = 0
        private const val SECOND_VIEW = 1
    }
}
