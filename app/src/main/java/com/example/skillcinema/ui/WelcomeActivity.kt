package com.example.skillcinema.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.WindowCompat
import com.example.skillcinema.App
import com.example.skillcinema.R
import com.example.skillcinema.databinding.ActivityWelcomeBinding
import com.example.skillcinema.domain.SharedPreferencesManager
import javax.inject.Inject

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var animation: Animation

    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    init {
        App.getAppComponent().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
//        Lang(this).upDateRes(sharedPreferencesManager.getLangId())
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        Helper.insets(binding.root)

        animation = AnimationUtils.loadAnimation(this, R.anim.fadeout)
        animation.setAnimationListener(animationFadeOutListener)

        val thread: Thread = object : Thread() {
            override fun run() {
                super.run()
                try {
                    sleep(2000)
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {

                    if (sharedPreferencesManager.getWasOpened()) {
                        Navigator.startHomeActivity(this@WelcomeActivity)
                    } else {
                        Navigator.startOnBoardingActivity(this@WelcomeActivity)
                    }


                }
            }
        }

        thread.start()

    }

    private var animationFadeOutListener: Animation.AnimationListener =
        object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
        }
}