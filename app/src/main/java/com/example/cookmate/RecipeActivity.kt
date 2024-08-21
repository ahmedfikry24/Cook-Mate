package com.example.cookmate

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowInsetsController
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class RecipeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var navHost: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recipe)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupThemeAppearance()
        initViews()
        setupBottomNav()
        setupNavDrawer()
    }

    private fun setupThemeAppearance() {
        window.navigationBarColor = getColor(R.color.background)
        window.statusBarColor = getColor(R.color.primary)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(
                0,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        }
    }

    private fun initViews() {
        navHost =
            supportFragmentManager.findFragmentById(R.id.user_main_container) as NavHostFragment
        navController = navHost.navController
        drawerLayout = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.toolbar)
        appBarConfiguration =
            AppBarConfiguration(setOf(R.id.aboutFragment, R.id.sign_out), drawerLayout)
        navigationView = findViewById(R.id.nav_view)
    }

    private fun setupNavDrawer() {
        val actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.close, R.string.open)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        actionBarDrawerToggle.drawerArrowDrawable.color = getColor(R.color.background)
        navigationView.setNavigationItemSelectedListener(this)
    }

    private fun setupBottomNav() {
        findViewById<BottomNavigationView>(R.id.bottom_nav).setupWithNavController(navController)
        findViewById<BottomNavigationView>(R.id.bottom_nav).setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    if (navController.currentDestination?.id != R.id.homeFragment) {
                        navController.popBackStack(R.id.homeFragment, false)
                    }
                    true
                }

                R.id.searchFragment -> {
                    if (navController.currentDestination?.id != R.id.searchFragment) {
                        navController.navigate(R.id.searchFragment)
                    }
                    true
                }

                R.id.favouriteFragment -> {
                    if (navController.currentDestination?.id != R.id.favouriteFragment) {
                        navController.navigate(R.id.favouriteFragment)
                    }
                    true
                }

                else -> false
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.aboutFragment -> {
                drawerLayout.closeDrawer(GravityCompat.START)
            }

            R.id.sign_out -> {
                drawerLayout.closeDrawer(GravityCompat.START)
            }
        }
        return true
    }
}