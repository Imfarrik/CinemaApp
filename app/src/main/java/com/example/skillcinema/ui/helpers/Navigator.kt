package com.example.skillcinema.ui.helpers

import android.content.Context
import android.content.Intent
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.skillcinema.R
import com.example.skillcinema.ui.pages.actor_page.ActorFragmentDirections
import com.example.skillcinema.ui.pages.home_page.HomeFragmentDirections
import com.example.skillcinema.ui.pages.home_page.HomePageActivity
import com.example.skillcinema.ui.pages.on_boarding_page.OnBoardingActivity
import com.example.skillcinema.ui.pages.single_image_page.SingleImageActivity
import com.example.skillcinema.ui.pages.single_movie_page.SingleMovieFragmentDirections


object Navigator {

    private fun navOptions(): NavOptions {
        return NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setEnterAnim(R.anim.slide_from_right)
            .setExitAnim(R.anim.slide_to_left)
            .setPopEnterAnim(R.anim.slide_from_left)
            .setPopExitAnim(R.anim.slide_to_right)
            .build()
    }

    fun startHomeActivity(activity: FragmentActivity) {
        val intent = Intent(activity, HomePageActivity::class.java)
        activity.startActivity(intent)
        ActivityCompat.finishAffinity(activity)
    }

    fun startOnBoardingActivity(activity: FragmentActivity) {
        val intent = Intent(activity, OnBoardingActivity::class.java)
        activity.startActivity(intent)
        ActivityCompat.finishAffinity(activity)
    }

    fun actionHomeFragmentToAllMoviesFragment(navController: NavController, title: String) {
        if (navController.currentDestination?.id == R.id.homeFragment) {
            navController.navigate(
                HomeFragmentDirections.actionHomeFragmentToAllMoviesFragment(title),
                navOptions()
            )
        }
    }

    fun actionHomeFragmentToSingleMovieFragment(navController: NavController, id: Int) {
        if (navController.currentDestination?.id == R.id.homeFragment) {
            navController.navigate(
                HomeFragmentDirections.actionHomeFragmentToSingleMovieFragment2(id),
                navOptions()
            )
        }
    }

    fun actionSingleMovieFragmentToActorFragment(navController: NavController, id: Int) {
        if (navController.currentDestination?.id == R.id.singleMovieFragment) {
            navController.navigate(
                SingleMovieFragmentDirections.actionSingleMovieFragmentToActorFragment(id),
                navOptions()
            )
        }
    }

    fun actionActorFragmentToSingleMovieFragment(navController: NavController, id: Int) {
        if (navController.currentDestination?.id == R.id.actorFragment) {
            navController.navigate(
                ActorFragmentDirections.actionActorFragmentToSingleMovieFragment(id),
                navOptions()
            )
        }
    }

    fun actionSingleMovieFragmentToGalleryFragment(navController: NavController) {
        if (navController.currentDestination?.id == R.id.singleMovieFragment) {
            navController.navigate(
                SingleMovieFragmentDirections.actionSingleMovieFragmentToGalleryFragment(),
                navOptions()
            )
        }
    }

    fun startImageActivity(context: Context, typeId: Int, imageId: Int) {
        val intent = Intent(context, SingleImageActivity::class.java)
        intent.putExtra("type_id", typeId)
        intent.putExtra("image_id", imageId)
        context.startActivity(intent)
    }


}