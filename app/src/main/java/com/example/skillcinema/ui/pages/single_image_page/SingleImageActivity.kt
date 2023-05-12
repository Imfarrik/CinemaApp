package com.example.skillcinema.ui.pages.single_image_page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.example.skillcinema.databinding.ActivitySingleImageBinding
import com.example.skillcinema.ui.adapters.single_image_adapters.FullImagePagingAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SingleImageActivity : AppCompatActivity() {

    private lateinit var vb: ActivitySingleImageBinding
    private val viewModel: SingleImageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivitySingleImageBinding.inflate(layoutInflater)
        setContentView(vb.root)

        val typeId = intent.getIntExtra("type_id", 1)
        val imageId = intent.getIntExtra("image_id", 1)

        vb.header.headerTitle.isVisible = false

        vb.header.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val adapter = FullImagePagingAdapter()

        vb.mainImage.adapter = adapter

        lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.CREATED) {

                viewModel.listPaging[typeId].collectLatest(adapter::submitData)



                adapter.loadStateFlow.map { combine ->
                    combine.refresh
                }.distinctUntilChanged().collect { loadState ->
                    if (loadState is LoadState.NotLoading) {
                        vb.mainImage.currentItem = imageId
                    }
                }

            }

        }


    }
}