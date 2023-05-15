package com.example.skillcinema.ui.pages.filmography_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skillcinema.databinding.FragmentFilmographyBinding
import com.example.skillcinema.domain.Constants
import com.example.skillcinema.model.data.apiSingleStaff.ApiSingleStaff
import com.example.skillcinema.model.data.apiSingleStaff.Film
import com.example.skillcinema.ui.adapters.filmography_page_adapter.MovieAdapter
import com.example.skillcinema.ui.adapters.filmography_page_adapter.TypeAdapter
import com.example.skillcinema.ui.helpers.Helper
import com.example.skillcinema.ui.helpers.Navigator
import com.google.gson.Gson

class FilmographyFragment : Fragment() {

    private lateinit var vb: FragmentFilmographyBinding
    private val viewModel: FilmographyViewModel by viewModels()
    private val args: FilmographyFragmentArgs by navArgs()

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

        val arg = Gson().fromJson(args.singleStuffItems, ApiSingleStaff::class.java)

        typeRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        typeRv.adapter = TypeAdapter(arg) {

            movieRv.layoutManager =
                GridLayoutManager(requireContext(), 3)
            movieRv.adapter = MovieAdapter(viewModel.repository, it) { id ->
                Navigator.actionFilmographyFragmentToSingleMovieFragment(findNavController(), id)
            }

        }


    }


}

