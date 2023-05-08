package com.example.skillcinema

import android.app.Application
import com.example.skillcinema.di.AppComponent
import com.example.skillcinema.di.AppModule
import com.example.skillcinema.di.DaggerAppComponent

class App : Application() {

    companion object {
        private lateinit var appComponent: AppComponent
        fun getAppComponent(): AppComponent = appComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

}