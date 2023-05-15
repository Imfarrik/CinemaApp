package com.example.skillcinema.ui.pages.single_movie_page

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.skillcinema.databinding.FragmentSingleMovieBinding
import com.example.skillcinema.domain.Constants
import com.example.skillcinema.model.data.apiSingleMovie.Country
import com.example.skillcinema.model.data.apiSingleMovie.Genre
import com.example.skillcinema.ui.helpers.Navigator
import com.example.skillcinema.ui.adapters.single_page_adapters.ImagesAdapter
import com.example.skillcinema.ui.adapters.single_page_adapters.SimilarListAdapter
import com.example.skillcinema.ui.adapters.single_page_adapters.StaffAdapter

class SingleMovieFragment : Fragment() {

    private lateinit var vb: FragmentSingleMovieBinding

    private val viewModel: SingleMovieViewModel by viewModels()

    private val args: SingleMovieFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        vb = FragmentSingleMovieBinding.inflate(layoutInflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(vb.root) { _, insets ->
            val navBarHeight = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
            vb.root.updatePadding(bottom = navBarHeight)
            insets
        }

        ViewCompat.setOnApplyWindowInsetsListener(vb.btnBack) { _, insets ->
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            vb.btnBack.updatePadding(top = statusBarHeight)
            insets
        }

        initView()
    }

    @SuppressLint("SetTextI18n")
    private fun initView() = with(vb) {

        viewModel.getStaff(args.movieId)

        btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        viewModel.movie.observe(viewLifecycleOwner) {

            if (it.type == Constants.TV_SERIES) {

                viewModel.getSeason(args.movieId)

                serialsContainer.isVisible = true

                viewModel.season.observe(viewLifecycleOwner) { season ->
                    serialsCount.text =
                        "${season.total} сезон, ${season.items.last().episodes.last().episodeNumber} серий"

                    all.setOnClickListener {
                        Navigator.actionSingleMovieFragmentToSeasonsFragment(
                            findNavController(),
                            season
                        )
                    }

                }


            }

            Glide.with(requireContext()).load(it.posterUrl).into(imageView)

            originalName.text = it.nameOriginal

            year.text = "${it.ratingImdb} IMDb, ${it.nameRu}"

            country.text = "${it.year}, ${convertMassiveToString(genres = it.genres)}"

            rusName.text =
                "${convertMassiveToString(country = it.countries)}, ${
                    convertMinutesToHours(
                        it.filmLength
                    )
                }, ${
                    if (it.ratingAgeLimits != null) {
                        convertToAgeFormat(
                            it.ratingAgeLimits
                        )
                    } else {
                        return@observe
                    }

                }"

            if (it.shortDescription != null) {

                slogan.text = it.shortDescription

            } else {
                slogan.text = it.slogan ?: it.nameRu
            }

            description.setOnClickListener {
                if (description.maxLines == 4) {
                    description.apply {
                        maxLines = Int.MAX_VALUE
                        ellipsize = null
                    }
                } else {
                    description.apply {
                        maxLines = 4
                        ellipsize = TextUtils.TruncateAt.END
                    }
                }
            }

            description.text = it.description

        }

        viewModel.staff.observe(viewLifecycleOwner) { staff ->


            actor.apply {
                type.text = "В фильме снимались"
                rv.layoutManager =
                    GridLayoutManager(requireContext(), 4, GridLayoutManager.HORIZONTAL, false)
                rv.adapter = StaffAdapter(staff, { actors, _ ->
                    count.text = actors.toString()
                }, { id ->
                    Navigator.actionSingleMovieFragmentToActorFragment(findNavController(), id)
                }, true)

            }

            staffLay.apply {
                type.text = "Над фильмом работали"
                rv.layoutManager =
                    GridLayoutManager(requireContext(), 2, GridLayoutManager.HORIZONTAL, false)
                rv.adapter = StaffAdapter(staff, { _, others ->
                    count.text = others.toString()
                }, { id ->
                    Navigator.actionSingleMovieFragmentToActorFragment(findNavController(), id)
                }, false)
            }

        }

        viewModel.images.observe(viewLifecycleOwner) {
            gallery.apply {
                type.text = "Галерея"
                allBtn.setOnClickListener {
                    viewModel.sharedPreferencesManager.saveMovieId(args.movieId)
                    Navigator.actionSingleMovieFragmentToGalleryFragment(findNavController())
                }
                count.text = it.items.size.toString()
                rv.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                rv.adapter = ImagesAdapter(it.items) {
                    Navigator.startImageActivity(requireContext(), 0, it)
                }
            }
        }

        viewModel.similar.observe(viewLifecycleOwner) {
            sameMovies.apply {
                type.text = "Похожие фильмы"
                count.text = it.items.size.toString()
                rv.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                rv.adapter = SimilarListAdapter(it.items)
            }
        }


    }

    private fun convertMassiveToString(
        country: List<Country>? = null,
        genres: List<Genre>? = null,
    ): String {
        val output = mutableListOf<String>()

        country?.let {
            output.addAll(it.map { country -> country.country })
        }

        genres?.let {
            output.addAll(it.map { genre -> genre.genre })
        }

        return output.joinToString(", ")
    }

    private fun convertToAgeFormat(input: String): String {
        return input.replace("age", "") + "+"
    }

    private fun convertMinutesToHours(minutes: Int): String {
        val hours = minutes / 60
        val remainingMinutes = minutes % 60

        return "$hours ч $remainingMinutes мин"
    }
}

