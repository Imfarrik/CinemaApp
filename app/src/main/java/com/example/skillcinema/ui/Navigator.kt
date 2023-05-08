package com.example.skillcinema.ui

import android.content.Intent
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.example.skillcinema.ui.home_page.HomePageActivity
import com.example.skillcinema.ui.on_boarding_page.OnBoardingActivity

object Navigator {

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



}