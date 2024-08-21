package com.example.cookmate.ui.home.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cookmate.R
import com.example.cookmate.ui.home.view_model.CategoryInfo
import com.example.cookmate.ui.home.view_model.HomeInteractions
import com.example.cookmate.ui.home.view_model.RecipeInfo
import com.example.cookmate.ui.utils.loadImageUrl
import com.google.android.material.tabs.TabLayout

class MainAdapter(
    private var categories: List<CategoryInfo>,
    private var recipesOfDay: List<RecipeInfo>,
    private val interactions: HomeInteractions,
) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    val recipesAdapter = HomeRecipesAdapter(listOf(), interactions)
    val favoriteAdapter = HomeFavoriteAdapter(listOf(), interactions)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return when (viewType) {
            FIRST_VIEW -> CategoriesTabsViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.home_categories_tabs, parent, false)
            )

            SECOND_VIEW -> RecipesViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.home_recipes, parent, false)
            )

            THIRD_VIEW -> RecipeOfDayViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.home_recipe_of_day, parent, false)
            )

            else -> FavoriteViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.home_favorite_recips, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        when (holder) {
            is CategoriesTabsViewHolder -> onBindCategoriesTabs(holder)
            is RecipesViewHolder -> onBindRecipes(holder)
            is RecipeOfDayViewHolder -> onBindRecipeOfDay(holder)
            is FavoriteViewHolder -> onBindFavorites(holder)
        }
    }

    @SuppressLint("InflateParams")
    private fun onBindCategoriesTabs(holder: CategoriesTabsViewHolder) {
        holder.apply {
            tabLayout.removeAllTabs()
            categories.forEachIndexed { index, category ->
                if (index == 0) {
                    interactions.onClickCategory(categories[index].name)
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
                    interactions.onClickCategory(categories[tab?.position ?: 0].name)
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

    fun updateCategories(newItems: List<CategoryInfo>) {
        val diffUtil = DiffUtil.calculateDiff(
            MainDiffUtil(
                categories,
                newItems,
                areItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
                areContentsTheSame = { oldItem, newItem -> oldItem == newItem }
            ))
        categories = newItems
        diffUtil.dispatchUpdatesTo(this)
    }

    private fun onBindRecipes(holder: RecipesViewHolder) {
        holder.apply {
            recycler.adapter = recipesAdapter
        }
    }

    private fun onBindRecipeOfDay(holder: RecipeOfDayViewHolder) {
        if (recipesOfDay.isNotEmpty())
            holder.apply {
                val item = recipesOfDay.first()
                image.loadImageUrl(item.url)
                title.text = item.name
                area.text = item.area
                category.text = item.category
                itemView.setOnClickListener { interactions.onClickRecipe(item.id) }
            }
    }

    fun updateRecipesOfDay(newItems: List<RecipeInfo>) {
        val diffUtil = DiffUtil.calculateDiff(
            MainDiffUtil(
                recipesOfDay,
                newItems,
                areItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
                areContentsTheSame = { oldItem, newItem -> oldItem == newItem }
            ))
        recipesOfDay = newItems
        diffUtil.dispatchUpdatesTo(this)
    }

    private fun onBindFavorites(holder: FavoriteViewHolder) {
        holder.apply {
            recycler.adapter = favoriteAdapter
            sectionTitle.isVisible = favoriteAdapter.recipes.isNotEmpty()
            more.setOnClickListener { interactions.onClickFavoriteMore() }
        }
    }

    override fun getItemCount() = 4
    override fun getItemViewType(position: Int) = position

    abstract class MainViewHolder(view: View) : ViewHolder(view)
    class CategoriesTabsViewHolder(view: View) : MainViewHolder(view) {
        val tabLayout: TabLayout = view.findViewById(R.id.tab_layout)
    }

    class RecipesViewHolder(view: View) : MainViewHolder(view) {
        val recycler: RecyclerView = view.findViewById(R.id.category_recipes_recycler)
    }

    class RecipeOfDayViewHolder(view: View) : MainViewHolder(view) {
        val image: AppCompatImageView = view.findViewById(R.id.image_recipe)
        val title: TextView = view.findViewById(R.id.title_recipe)
        val area: TextView = view.findViewById(R.id.area_recipe)
        val category: TextView = view.findViewById(R.id.category_recipe)
    }

    class FavoriteViewHolder(view: View) : MainViewHolder(view) {
        val recycler: RecyclerView = view.findViewById(R.id.home_favorite_recycler)
        val sectionTitle: LinearLayout = view.findViewById(R.id.section_title)
        val more: TextView = view.findViewById(R.id.favorite_more)
    }

    companion object {
        private const val FIRST_VIEW = 0
        private const val SECOND_VIEW = 1
        private const val THIRD_VIEW = 2
    }
}
