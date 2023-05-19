package com.example.skillcinema.ui.pages.all_movies_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.skillcinema.databinding.FragmentAllMoviesBinding
import com.example.skillcinema.model.data.apiSingleStaff.ApiSingleStaff
import com.example.skillcinema.model.repository.State
import com.example.skillcinema.ui.adapters.all_movies.*
import com.example.skillcinema.ui.helpers.Helper
import com.example.skillcinema.ui.helpers.Navigator
import com.google.gson.Gson
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AllMoviesFragment : Fragment() {

    private lateinit var vb: FragmentAllMoviesBinding
    private val viewModel: AllMoviesViewModel by viewModels()
    private val args: AllMoviesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        vb = FragmentAllMoviesBinding.inflate(layoutInflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Helper.insets(vb.root)

        initView()

    }

    private fun initView() = with(vb) {

        header.apply {
            try {
                if (args.headerTitle.toInt() in 0..4) {

                    viewModel.getAllTypes(args.headerTitle)

                    viewModel.repository.title.observe(viewLifecycleOwner) { title ->
                        headerTitle.text = title
                    }
                }
            } catch (_: java.lang.Exception) {

            }

            btnBack.setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }

        }


        movieRv.apply {

            layoutManager = GridLayoutManager(requireContext(), 2)

            when (args.headerTitle) {
                "0" -> {
                    getAllTypes()
                    viewModel.repository.newMovies.observe(viewLifecycleOwner) {
                        adapter =
                            NewListAdapter(viewModel.appDatabase, it.items, true, {}, { film_id ->
                                Navigator.actionAllMoviesFragmentToSingleMovieFragment(
                                    findNavController(), film_id
                                )
                            })
                    }
                }
                "1" -> {
                    getAllTypes()
                    val adapterPaging = TopPagePagingAdapter()
                    adapter = adapterPaging
                    lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.CREATED) {
                            viewModel.popular.collectLatest(adapterPaging::submitData)
                        }
                    }
                }
                "2" -> {
                    getAllTypes()
                    val adapterPaging = RandomPagePagingAdapter()
                    adapter = adapterPaging
                    lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.CREATED) {
                            viewModel.random.collectLatest(adapterPaging::submitData)
                        }
                    }
                }
                "3" -> {
                    getAllTypes()
                    val adapterPaging = TopPagePagingAdapter()
                    adapter = adapterPaging
                    lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.CREATED) {
                            viewModel.top250.collectLatest(adapterPaging::submitData)
                        }
                    }
                }
                "4" -> {
                    getAllTypes()
                    val adapterPaging = RandomPagePagingAdapter()
                    adapter = adapterPaging
                    lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.CREATED) {
                            viewModel.serials.collectLatest(adapterPaging::submitData)
                        }
                    }
                }
                "5" -> {
                    viewModel.getWatched()
                    header.headerTitle.text = "Просмотрено"
                    viewModel.allWatched.observe(viewLifecycleOwner) {
                        adapter = WatchedAdapter(it) { film_id ->
                            Navigator.actionAllMoviesFragmentToSingleMovieFragment(
                                findNavController(), film_id
                            )
                        }
                    }

                }
                else -> {
                    val listOfMovies = Gson().fromJson(args.headerTitle, ApiSingleStaff::class.java)
                    header.headerTitle.text = listOfMovies.nameRu ?: listOfMovies.nameEn
                    adapter = MovieAdapter(viewModel.repository, listOfMovies.films) { film_id ->
                        Navigator.actionAllMoviesFragmentToSingleMovieFragment(
                            findNavController(), film_id
                        )
                    }
                }
            }


        }


    }

    private fun getAllTypes() = with(vb) {
        viewModel.getAllTypes(args.headerTitle)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    if (it != null) {
                        when (it) {
                            State.Success -> {
                                loader.isVisible = false
                                mainContainer.isVisible = true
                            }
                            State.Loading -> {
                                mainContainer.isVisible = false
                                loader.isVisible = true
                            }
                            is State.ServerError -> {
                                Helper.setToast(requireContext(), it.message)
                            }
                        }
                    }
                }
            }
        }
    }


}