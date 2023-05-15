package com.example.skillcinema.ui.helpers

import android.content.Context
import android.content.Intent
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.skillcinema.R
import com.example.skillcinema.model.data.apiSeason.ApiSeason
import com.example.skillcinema.model.data.apiSingleStaff.ApiSingleStaff
import com.example.skillcinema.ui.pages.actor_page.ActorFragmentDirections
import com.example.skillcinema.ui.pages.all_movies_page.AllMoviesFragmentDirections
import com.example.skillcinema.ui.pages.filmography_page.FilmographyFragmentDirections
import com.example.skillcinema.ui.pages.filter_page.FilterActivity
import com.example.skillcinema.ui.pages.home_page.HomeFragmentDirections
import com.example.skillcinema.ui.pages.home_page.HomePageActivity
import com.example.skillcinema.ui.pages.on_boarding_page.OnBoardingActivity
import com.example.skillcinema.ui.pages.single_image_page.SingleImageActivity
import com.example.skillcinema.ui.pages.single_movie_page.SingleMovieFragmentDirections
import com.google.gson.Gson


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


    fun startHomeActivity(context: Context?) {
        val intent = Intent(context, HomePageActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        context?.startActivity(intent)
    }

    fun startOnBoardingActivity(context: Context?) {
        val intent = Intent(context, OnBoardingActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        context?.startActivity(intent)
    }

    fun startImageActivity(context: Context, typeId: Int, imageId: Int) {
        val intent = Intent(context, SingleImageActivity::class.java)
        intent.putExtra("type_id", typeId)
        intent.putExtra("image_id", imageId)
        context.startActivity(intent)
    }

    fun startFilterActivity(context: Context) {
        val intent = Intent(context, FilterActivity::class.java)
        context.startActivity(intent)
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

    fun actionSingleMovieFragmentToSeasonsFragment(
        navController: NavController,
        apiSeason: ApiSeason,
    ) {
        val seasons = Gson().toJson(apiSeason)
        if (navController.currentDestination?.id == R.id.singleMovieFragment) {
            navController.navigate(
                SingleMovieFragmentDirections.actionSingleMovieFragmentToSeasonsFragment(seasons),
                navOptions()
            )
        }
    }

    fun actionActorFragmentToFilmographyFragment(
        navController: NavController,
        data: ApiSingleStaff,
    ) {

        val listOfMovies = Gson().toJson(data)

        if (navController.currentDestination?.id == R.id.actorFragment) {
            navController.navigate(
                ActorFragmentDirections.actionActorFragmentToFilmographyFragment(listOfMovies),
                navOptions()
            )
        }
    }

    fun actionFilmographyFragmentToSingleMovieFragment(navController: NavController, id: Int) {
        if (navController.currentDestination?.id == R.id.filmographyFragment) {
            navController.navigate(
                FilmographyFragmentDirections.actionFilmographyFragmentToSingleMovieFragment(id),
                navOptions()
            )
        }
    }

    fun actionAllMoviesFragmentToSingleMovieFragment(navController: NavController, id: Int) {
        if (navController.currentDestination?.id == R.id.allMoviesFragment) {
            navController.navigate(
                AllMoviesFragmentDirections.actionAllMoviesFragmentToSingleMovieFragment(id),
                navOptions()
            )
        }
    }

    fun actionActorFragmentToAllMoviesFragment(
        navController: NavController,
        data: ApiSingleStaff,
    ) {

        val listOfMovies = Gson().toJson(data)

        if (navController.currentDestination?.id == R.id.actorFragment) {
            navController.navigate(
                ActorFragmentDirections.actionActorFragmentToAllMoviesFragment(listOfMovies),
                navOptions()
            )
        }
    }


}