package com.example.skillcinema.ui.pages.home_page

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skillcinema.databinding.FragmentHomeBinding
import com.example.skillcinema.databinding.MovieHolderBinding
import com.example.skillcinema.model.data.db.MoviesHolder
import com.example.skillcinema.model.repository.State
import com.example.skillcinema.ui.helpers.Helper
import com.example.skillcinema.ui.helpers.Navigator
import com.example.skillcinema.ui.adapters.home_adapter.FilmListAdapter
import com.example.skillcinema.ui.adapters.home_adapter.NewListAdapter
import com.example.skillcinema.ui.adapters.home_adapter.TopListAdapter
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var vb: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        vb = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Helper.insets(vb.root)

        initView()

        viewModel

    }

    private fun initView() = with(vb) {

        val types = listOf("Примьеры", "Популярное", "-", "Топ-250", "Сериалы")

        val viewHolders = listOf(
            newMovie, popular, USA, top, serials
        )

        for (i in viewHolders.indices) {
            viewHolders[i].apply {
                holderType.text = types[i]
                rvMovie.layoutManager = LinearLayoutManager(
                    requireContext(), LinearLayoutManager.HORIZONTAL, false
                )
                btnAll.setOnClickListener {
                    Navigator.actionHomeFragmentToAllMoviesFragment(
                        findNavController(),
                        i.toString()
                    )
                }
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            nestedScrollView.isVisible = !it
            loader.isVisible = it
        }

        viewModel.isSuccess.observe(viewLifecycleOwner) {

            loader.isVisible = !it
            nestedScrollView.isVisible = it
            initRv(viewHolders)

            for (i in viewHolders.indices) {
                viewModel.insert(
                    MoviesHolder(
                        viewHolders[i].holderType.text as String,
                        i
                    )
                )
            }

        }

        viewModel.onError.observe(viewLifecycleOwner) {
            Helper.setToast(requireContext(), it)
        }

    }

    private fun initRv(viewHolders: List<MovieHolderBinding>) {
        viewModel.repository.newMovies.observe(viewLifecycleOwner) {
            viewHolders[0].rvMovie.adapter = NewListAdapter(it.items, false, {
                Navigator.actionHomeFragmentToAllMoviesFragment(findNavController(), "0")
            }, { movie_id ->
                Navigator.actionHomeFragmentToSingleMovieFragment(findNavController(), movie_id)
            })
        }

        viewModel.repository.topMovies.observe(viewLifecycleOwner) {
            viewHolders[1].rvMovie.adapter = TopListAdapter(it.films, { movie_id ->
                Navigator.actionHomeFragmentToSingleMovieFragment(findNavController(), movie_id)
            }, {
                Navigator.actionHomeFragmentToAllMoviesFragment(findNavController(), "1")
            })
        }

        viewModel.repository.randomName.observe(viewLifecycleOwner) {
            viewHolders[2].holderType.text = it
        }

        viewModel.repository.randomMovies.observe(viewLifecycleOwner) {
            viewHolders[2].rvMovie.adapter = FilmListAdapter(it.items, { movie_id ->
                Navigator.actionHomeFragmentToSingleMovieFragment(findNavController(), movie_id)
            }, {
                Navigator.actionHomeFragmentToAllMoviesFragment(findNavController(), "2")
            })
            viewModel.insert(MoviesHolder(viewHolders[2].holderType.text as String, 2))
        }

        viewModel.repository.top250Movies.observe(viewLifecycleOwner) {
            viewHolders[3].rvMovie.adapter = TopListAdapter(it.films, { movie_id ->
                Navigator.actionHomeFragmentToSingleMovieFragment(
                    findNavController(),
                    movie_id
                )
            }, {
                Navigator.actionHomeFragmentToAllMoviesFragment(findNavController(), "3")
            })
        }

        viewModel.repository.topSeries.observe(viewLifecycleOwner) {
            viewHolders[4].rvMovie.adapter = FilmListAdapter(it.items, { movie_id ->
                Navigator.actionHomeFragmentToSingleMovieFragment(findNavController(), movie_id)
            }, {
                Navigator.actionHomeFragmentToAllMoviesFragment(findNavController(), "4")
            })
        }
    }


}