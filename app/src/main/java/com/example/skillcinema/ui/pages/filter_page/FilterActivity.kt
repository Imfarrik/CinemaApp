package com.example.skillcinema.ui.pages.filter_page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.skillcinema.databinding.ActivityFilterBinding

class FilterActivity : AppCompatActivity() {

    private lateinit var vb: ActivityFilterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityFilterBinding.inflate(layoutInflater)
        setContentView(vb.root)



    }
}