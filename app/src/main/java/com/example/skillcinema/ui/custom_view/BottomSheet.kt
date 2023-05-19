package com.example.skillcinema.ui.custom_view

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.skillcinema.R
import com.example.skillcinema.databinding.BottomSheetForCollectionsBinding
import com.example.skillcinema.model.data.apiSingleMovie.ApiSingleMovie
import com.example.skillcinema.ui.adapters.bottom_sheet_adapter.CollectionAdapter
import com.example.skillcinema.ui.pages.single_movie_page.SingleMovieViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheet(private val apiSingleMovie: ApiSingleMovie) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetForCollectionsBinding
    private val viewModel: SingleMovieViewModel by viewModels()

    override fun getTheme(): Int = R.style.BottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = BottomSheetForCollectionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        Glide.with(requireContext())
            .load(apiSingleMovie.posterUrlPreview)
            .into(binding.imageView)

        binding.movieName.text = apiSingleMovie.nameRu ?: apiSingleMovie.nameEn
        binding.movieType.text =
            "${apiSingleMovie.countries.first().country}, ${apiSingleMovie.genres.first().genre}"

        binding.btnClose.setOnClickListener {
            this@BottomSheet.dismiss()
        }

        viewModel.getDBData()

        viewModel.allCollections.observe(viewLifecycleOwner) {
            binding.rvCollection.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.rvCollection.adapter =
                CollectionAdapter(viewModel.appDatabase, apiSingleMovie, it)
        }

        binding.btnNewCollection.setOnClickListener {
            this@BottomSheet.dismiss()
//                showNewDialog(apiSingleMovie)
        }

    }

    fun dismissListener(update: () -> Unit) {
        dialog?.setOnDismissListener {
            update()
        }
    }


    companion object {
        const val TAG = "hello"


//        val bottomSheet = BottomSheet(apiSingleMovie)
//        val fragmentManager = (activity as FragmentActivity).supportFragmentManager
//        bottomSheet.dismissListener {
//            viewModel.getDBData()
//            initView()
//        }
//        fragmentManager.let { bottomSheet.show(it, BottomSheet.TAG) }
    }

}