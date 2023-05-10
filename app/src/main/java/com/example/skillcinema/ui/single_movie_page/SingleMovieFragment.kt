package com.example.skillcinema.ui.single_movie_page

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.skillcinema.R
import com.example.skillcinema.databinding.FragmentSingleMovieBinding
import com.example.skillcinema.ui.Helper

class SingleMovieFragment : Fragment() {

    private lateinit var vb: FragmentSingleMovieBinding

    private val viewModel: SingleMovieViewModel by viewModels()

    private val args: SingleMovieFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vb = FragmentSingleMovieBinding.inflate(layoutInflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Helper.insets(vb.root)

        initView()
    }

    private fun initView() = with(vb) {

        viewModel.getSingleMovie(args.movieId)

        viewModel.movie.observe(viewLifecycleOwner) {

            Glide.with(requireContext())
                .load(it.posterUrl)
                .into(imageView)

            originalName.text = it.nameOriginal
            year.text = "${it.ratingImdb}, ${it.nameRu}"
            country.text = "${it.year}, ${it.genres[0].genre}"
            rusName.text =
                "${it.countries[0].country}, ${it.filmLength / 60}, ${it.ratingAgeLimits}"

            slogan.text = it.slogan
            description.text = it.description

        }


    }
}