package com.example.skillcinema.ui.pages.home_page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.skillcinema.R
import com.example.skillcinema.databinding.ActivityHomePageBinding

class HomePageActivity : AppCompatActivity() {

    private lateinit var vb: ActivityHomePageBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(vb.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        ViewCompat.setOnApplyWindowInsetsListener(vb.root) { _, insets ->
            val navBarHeight = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
            vb.root.updatePadding(bottom = navBarHeight)
            insets
        }

        vb.bottomNavigationView.setOnApplyWindowInsetsListener { _, inset ->
            vb.bottomNavigationView.updatePadding(top = 0, bottom = 0)
            inset
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.home_activity_fragment_container_view) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView = vb.bottomNavigationView
        bottomNavigationView.setupWithNavController(navController)


    }
}