package com.example.cookmate.ui.recipe_details

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.cookmate.R
import com.example.cookmate.data.local.RoomManager
import com.example.cookmate.data.remote.RetrofitManager
import com.example.cookmate.data.repository.RepositoryImpl
import com.example.cookmate.data.source.LocalDataSourceImpl
import com.example.cookmate.data.source.RemoteDataSourceImpl
import com.example.cookmate.ui.recipe_details.view_model.RecipeDetailsViewModel
import com.example.cookmate.ui.recipe_details.view_model.RecipeDetailsViewModelFactory
import com.example.cookmate.ui.utils.loadImageUrl
import com.nex3z.flowlayout.FlowLayout

class RecipeDetailsFragment : Fragment() {
    private val remoteDataSource by lazy { RemoteDataSourceImpl(RetrofitManager.service) }
    private val localDataSource by lazy { LocalDataSourceImpl(RoomManager.getInit(requireContext())) }
    private val repository by lazy { RepositoryImpl(remoteDataSource, localDataSource) }
    private val viewModel by viewModels<RecipeDetailsViewModel> {
        RecipeDetailsViewModelFactory(
            repository
        )
    }
    private val args: RecipeDetailsFragmentArgs by navArgs()
    private lateinit var image: AppCompatImageView
    private lateinit var name: TextView
    private lateinit var instructions: TextView
    private lateinit var tags: LinearLayout
    private lateinit var ingredients: FlowLayout
    private lateinit var favoriteIcon: AppCompatImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getRecipeInfo(args.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        viewModelObservers()
    }

    private fun initViews(view: View) {
        image = view.findViewById(R.id.image_recipe)
        name = view.findViewById(R.id.title_recipe)
        instructions = view.findViewById(R.id.instructions)
        tags = view.findViewById(R.id.tags)
        ingredients = view.findViewById(R.id.ingredients)
        favoriteIcon = view.findViewById(R.id.favorite_icon)
    }

    private fun viewModelObservers() {
        viewModel.recipe.observe(viewLifecycleOwner) {
            image.loadImageUrl(it.url)
            name.text = it.name
            instructions.text = it.instructions
            favoriteIcon.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_favorite
                )
            )
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
                    ).apply {

                        setMargins(8, 0, 8, 0)
                    }

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
}