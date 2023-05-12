package com.example.skillcinema.ui.pages.filmography_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.skillcinema.databinding.FragmentFilmographyBinding
import com.example.skillcinema.ui.helpers.Helper

class FilmographyFragment : Fragment() {

    private lateinit var vb: FragmentFilmographyBinding
    private val viewModel: FilmographyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        vb = FragmentFilmographyBinding.inflate(layoutInflater)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Helper.insets(vb.root)

        initView()

    }

    private fun initView() = with(vb) {

        header.headerTitle.text = "Фильмография"

        header.btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }


    }


}