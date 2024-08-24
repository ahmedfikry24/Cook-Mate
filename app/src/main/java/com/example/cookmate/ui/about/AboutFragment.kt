package com.example.cookmate.ui.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.cookmate.R
import com.example.cookmate.RecipeActivity

class AboutFragment : Fragment() {

    private lateinit var descriptionTextView: TextView
    private lateinit var showMoreButton: Button
    private lateinit var teamMemberImageView1: ImageView
    private lateinit var teamMemberImageView2: ImageView
    private lateinit var teamMemberImageView3: ImageView
    private lateinit var teamMemberLinkedInButton1: Button
    private lateinit var teamMemberLinkedInButton2: Button
    private lateinit var teamMemberLinkedInButton3: Button

    private var isExpanded = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onResume() {
        super.onResume()
        val recipeActivity = activity as RecipeActivity
        recipeActivity.controlNavDrawerVisibility(false)
        recipeActivity.controlBottomNavVisibility(false)
    }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            initViews(view)
            loadTeamMemberImages()
        }

        private fun initViews(view: View) {
            descriptionTextView = view.findViewById(R.id.aboutDescriptionTextView)
            showMoreButton = view.findViewById(R.id.showMoreButton)
            teamMemberImageView1 = view.findViewById(R.id.teamMemberImageView1)
            teamMemberImageView2 = view.findViewById(R.id.teamMemberImageView2)
            teamMemberImageView3 = view.findViewById(R.id.teamMemberImageView3)
            teamMemberLinkedInButton1 = view.findViewById(R.id.teamMemberLinkedInButton1)
            teamMemberLinkedInButton2 = view.findViewById(R.id.teamMemberLinkedInButton2)
            teamMemberLinkedInButton3 = view.findViewById(R.id.teamMemberLinkedInButton3)

            showMoreButton.setOnClickListener {
                if (isExpanded) {
                    descriptionTextView.maxLines = 4
                    showMoreButton.text = getString(R.string.show_more)
                } else {
                    descriptionTextView.maxLines = Int.MAX_VALUE
                    showMoreButton.text = getString(R.string.show_less)
                }
                isExpanded = !isExpanded
            }

            val backIcon: View = view.findViewById(R.id.back_icon)
            backIcon.setOnClickListener {
                // Pop the current fragment from the back stack
                findNavController().popBackStack()
            }

            teamMemberLinkedInButton1.setOnClickListener {
                openLinkedInProfile("https://www.linkedin.com/in/ahmedfikryelshimi")
            }
            teamMemberLinkedInButton2.setOnClickListener {
                openLinkedInProfile("https://www.linkedin.com/in/mostafa-mohamed-9a923b233")
            }
            teamMemberLinkedInButton3.setOnClickListener {
                openLinkedInProfile("https://www.linkedin.com/in/michaellmounir")
            }
        }

        private fun loadTeamMemberImages() {
            Glide.with(this)
                .load("https://firebasestorage.googleapis.com/v0/b/swift-bargain.appspot.com/o/auth%2FPicsart_22-10-07_06-01-46-074.png?alt=media&token=02a98ddb-1428-42cb-9977-bf9a26883a1b")
                .transform(CircleCrop())
                .into(teamMemberImageView1)
            Glide.with(this)
                .load("https://firebasestorage.googleapis.com/v0/b/swift-bargain.appspot.com/o/auth%2F01%20copy.JPG?alt=media&token=c882418b-fe24-49bc-95f3-a096cea0fb54")
                .transform(CircleCrop())
                .into(teamMemberImageView2)
            Glide.with(this)
                .load("https://firebasestorage.googleapis.com/v0/b/swift-bargain.appspot.com/o/auth%2FIMG_49831.jpg?alt=media&token=0da7b56f-4388-438b-b97f-63c6ab33b2c5")
                .transform(CircleCrop())
                .into(teamMemberImageView3)
        }

        private fun openLinkedInProfile(url: String) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }



}