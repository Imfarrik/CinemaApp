package com.example.skillcinema.ui.pages.all_movies_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.skillcinema.databinding.FragmentAllMoviesCollectionBinding
import com.example.skillcinema.ui.adapters.all_movies.*
import com.example.skillcinema.ui.helpers.Helper
import com.example.skillcinema.ui.helpers.Navigator

class AllMoviesCollectionFragment : Fragment() {

    private lateinit var vb: FragmentAllMoviesCollectionBinding

    private val args: AllMoviesCollectionFragmentArgs by navArgs()

    private val viewModel: AllMoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        vb = FragmentAllMoviesCollectionBinding.inflate(layoutInflater)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Helper.insets(vb.root)

        initView()
    }

    private fun initView() = with(vb) {

        header.btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        movieRv.layoutManager = GridLayoutManager(requireContext(), 2)

        when (args.title) {
            "1" -> {
                viewModel.getInterested()
                header.headerTitle.text = "Вам было интересно"

                header.btnClear.isVisible = true

                header.btnClear.setOnClickListener {
                    viewModel.clearInterested()
                    Helper.setToast(requireContext(), "Список очищен")
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }

                viewModel.allInterested.observe(viewLifecycleOwner) {
                    movieRv.adapter = InterestedAdapter(it) { film_id ->
                        Navigator.actionAllMoviesFragmentToSingleMovieFragment(
                            findNavController(), film_id
                        )
                    }
                }
            }
            "2" -> {
                viewModel.getWatched()
                header.headerTitle.text = "Просмотрено"
                viewModel.allWatched.observe(viewLifecycleOwner) {
                    movieRv.adapter = WatchedAdapter(it) { film_id ->
                        Navigator.actionAllMoviesFragmentToSingleMovieFragment(
                            findNavController(), film_id
                        )
                    }
                }

            }
            else -> {

                header.headerTitle.text = args.title

                when (args.title) {
                    "Хочу посмотреть" -> {
                        viewModel.getSaved()
                        viewModel.allSaved.observe(viewLifecycleOwner) {
                            movieRv.adapter = SavedMoviesAdapter(it) { film_id ->
                                Navigator.actionAllMoviesFragmentToSingleMovieFragment(
                                    findNavController(), film_id
                                )
                            }
                        }
                    }
                    "Любимое" -> {
                        viewModel.getLiked()
                        viewModel.allLiked.observe(viewLifecycleOwner) {
                            movieRv.adapter = LikedMoviesAdapter(it) { film_id ->
                                Navigator.actionAllMoviesFragmentToSingleMovieFragment(
                                    findNavController(), film_id
                                )
                            }
                        }
                    }
                    else -> {
                        viewModel.getAllMoviesInCollection(args.title)
                        viewModel.allMoviesInCollection.observe(viewLifecycleOwner) {
                            movieRv.adapter = MovieInCollectionAdapter(it) { film_id ->
                                Navigator.actionAllMoviesFragmentToSingleMovieFragment(
                                    findNavController(), film_id
                                )
                            }
                        }
                    }
                }


            }
        }

    }


}