package com.example.skillcinema.di

import com.example.skillcinema.ui.WelcomeActivity
import com.example.skillcinema.ui.home_page.HomeViewModel
import com.example.skillcinema.ui.home_page.all_movies.AllMoviesViewModel
import com.example.skillcinema.ui.on_boarding_page.OnBoardingFragment
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(activity: WelcomeActivity)
    fun inject(viewModel: HomeViewModel)
    fun inject(onBoardingFragment: OnBoardingFragment)
    fun inject(viewModel: AllMoviesViewModel)

}