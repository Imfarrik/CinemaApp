package com.example.skillcinema.ui.pages.on_boarding_page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import androidx.navigation.fragment.NavHostFragment
import com.example.skillcinema.R
import com.example.skillcinema.databinding.ActivityOnBoardingBinding

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.on_boarding_activity_fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController
        navController
            .setGraph(R.navigation.on_boarding_nav_graph)


    }
}