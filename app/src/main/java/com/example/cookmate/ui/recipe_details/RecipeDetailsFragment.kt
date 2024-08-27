package com.example.cookmate.ui.recipe_details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.navArgs
import com.example.cookmate.R
import com.example.cookmate.RecipeActivity
import com.example.cookmate.ui.base.BaseFragment
import com.example.cookmate.ui.recipe_details.view_model.RecipeDetailsViewModel
import com.example.cookmate.ui.utils.loadImageUrl
import com.nex3z.flowlayout.FlowLayout

class RecipeDetailsFragment : BaseFragment<RecipeDetailsViewModel>() {

    private lateinit var image: AppCompatImageView
    private lateinit var name: TextView
    private lateinit var instructions: TextView
    private lateinit var tags: LinearLayout
    private lateinit var ingredients: FlowLayout
    private lateinit var favoriteIcon: AppCompatImageView
    private lateinit var backIcon: AppCompatImageView
    private lateinit var playVideoIcon: AppCompatImageView
    private val args: RecipeDetailsFragmentArgs by navArgs()

    override val fragmentId = R.layout.fragment_recipe_details
    override val viewModelClass = RecipeDetailsViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getRecipeInfo(args.id)
    }

    override fun onResume() {
        super.onResume()
        val recipeActivity = activity as RecipeActivity
        recipeActivity.controlNavDrawerVisibility(false)
        recipeActivity.controlBottomNavVisibility(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    override fun initViews(view: View) {
        image = view.findViewById(R.id.image_recipe)
        name = view.findViewById(R.id.title_recipe)
        instructions = view.findViewById(R.id.instructions)
        tags = view.findViewById(R.id.tags)
        ingredients = view.findViewById(R.id.ingredients)
        favoriteIcon = view.findViewById(R.id.favorite_icon)
        backIcon = view.findViewById(R.id.back_icon)
        playVideoIcon = view.findViewById(R.id.play_video_icon)
    }

    override fun viewModelObservers() {
        viewModel.recipe.observe(viewLifecycleOwner) {
            image.loadImageUrl(it.imageUrl)
            name.text = it.name
            instructions.text = it.instructions
            if (it.isFavorite) setFavoriteIconDrawable(R.drawable.ic_favorite)

            for (tag in it.tags) {
                if (tag.isNotBlank()) {
                    val textView = TextView(requireContext()).apply {
                        text = tag
                        textSize = 16f
                        typeface = ResourcesCompat.getFont(context, R.font.poppins_regular)
                        setPadding(8, 4, 8, 4)
                        gravity = Gravity.CENTER
                        background = ContextCompat.getDrawable(context, R.drawable.ingredient_shape)
                    }
                    val layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply { setMargins(8, 0, 8, 0) }
                    tags.addView(textView, layoutParams)
                }
            }

            for (ingredient in it.ingredients) {
                if (ingredient.isNotBlank()) {
                    val textView = TextView(requireContext()).apply {
                        text = ingredient
                        textSize = 16f
                        typeface = ResourcesCompat.getFont(context, R.font.poppins_regular)
                        setPadding(8, 4, 8, 4)
                        gravity = Gravity.CENTER
                        background = ContextCompat.getDrawable(context, R.drawable.ingredient_shape)
                    }
                    ingredients.addView(textView)
                }
            }

        }
    }

    private fun initListeners() {
        backIcon.setOnClickListener { navController.popBackStack() }
        playVideoIcon.setOnClickListener { openVideoUrl() }
        favoriteIcon.setOnClickListener {
            if (viewModel.isFavorite) setFavoriteIconDrawable(R.drawable.ic_unfavorite)
            else setFavoriteIconDrawable(R.drawable.ic_favorite)
            viewModel.onClickFavorite()
        }
    }

    private fun setFavoriteIconDrawable(drawableId: Int) {
        favoriteIcon.setImageDrawable(ContextCompat.getDrawable(requireContext(), drawableId))
    }

    private fun openVideoUrl() {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(viewModel.recipe.value?.videoUrl ?: "http://www.google.com")
        )
        startActivity(intent)
    }
}