package com.example.cookmate

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

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
        initViews()
        setupBottomNav()
        setupNavDrawer()
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
        navHost = supportFragmentManager.findFragmentById(R.id.user_main_container) as NavHostFragment
        navController = navHost.navController
        findViewById<BottomNavigationView>(R.id.bottom_nav).setupWithNavController(navController)
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