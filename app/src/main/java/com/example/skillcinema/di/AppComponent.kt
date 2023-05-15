package com.example.skillcinema.di

import com.example.skillcinema.ui.pages.welcome_page.WelcomeActivity
import com.example.skillcinema.ui.pages.actor_page.ActorViewModel
import com.example.skillcinema.ui.pages.home_page.HomeViewModel
import com.example.skillcinema.ui.pages.all_movies_page.AllMoviesViewModel
import com.example.skillcinema.ui.pages.filmography_page.FilmographyViewModel
import com.example.skillcinema.ui.pages.gallery_page.GalleryViewModel
import com.example.skillcinema.ui.pages.on_boarding_page.OnBoardingFragment
import com.example.skillcinema.ui.pages.search_page.SearchViewModel
import com.example.skillcinema.ui.pages.seasons_page.SeasonsViewModel
import com.example.skillcinema.ui.pages.single_image_page.SingleImageViewModel
import com.example.skillcinema.ui.pages.single_movie_page.SingleMovieViewModel
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(activity: WelcomeActivity)
    fun inject(viewModel: HomeViewModel)
    fun inject(onBoardingFragment: OnBoardingFragment)
    fun inject(viewModel: AllMoviesViewModel)
    fun inject(viewModel: SingleMovieViewModel)
    fun inject(viewModel: ActorViewModel)
    fun inject(viewModel: GalleryViewModel)
    fun inject(viewModel: SingleImageViewModel)
    fun inject(viewModel: FilmographyViewModel)
    fun inject(viewModel: SeasonsViewModel)
    fun inject(viewModel: SearchViewModel)

}