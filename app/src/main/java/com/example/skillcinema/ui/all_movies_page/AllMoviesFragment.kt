package com.example.skillcinema.ui.all_movies_page

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
import com.example.skillcinema.model.repository.State
import com.example.skillcinema.ui.Helper
import com.example.skillcinema.ui.adapters.all_movies.RandomPagePagingAdapter
import com.example.skillcinema.ui.adapters.all_movies.TopPagePagingAdapter
import com.example.skillcinema.ui.adapters.home_adapter.NewListAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AllMoviesFragment : Fragment() {

    private lateinit var vb: FragmentAllMoviesBinding
    private val viewModel: AllMoviesViewModel by viewModels()
    private val args: AllMoviesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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

        viewModel.getAllTypes(args.headerTitle)

        header.apply {

            viewModel.repository.title.observe(viewLifecycleOwner) { title ->
                headerTitle.text = title
            }

            btnBack.setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }

        }

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

        movieRv.apply {

            layoutManager = GridLayoutManager(requireContext(), 3)

            when (args.headerTitle.toInt()) {
                0 -> {
                    viewModel.repository.newMovies.observe(viewLifecycleOwner) {
                        adapter = NewListAdapter(it.items, true, {}, { film_id ->
                            findNavController().navigate(
                                AllMoviesFragmentDirections.actionAllMoviesFragmentToSingleMovieFragment(
                                    film_id
                                )
                            )
                        })
                    }
                }
                1 -> {
                    val adapterPaging = TopPagePagingAdapter()
                    adapter = adapterPaging
                    lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.CREATED) {
                            viewModel.popular.collectLatest(adapterPaging::submitData)
                        }
                    }
                }
                2 -> {
                    val adapterPaging = RandomPagePagingAdapter()
                    adapter = adapterPaging
                    lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.CREATED) {
                            viewModel.random.collectLatest(adapterPaging::submitData)
                        }
                    }
                }
                3 -> {
                    val adapterPaging = TopPagePagingAdapter()
                    adapter = adapterPaging
                    lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.CREATED) {
                            viewModel.top250.collectLatest(adapterPaging::submitData)
                        }
                    }
                }
                4 -> {
                    val adapterPaging = RandomPagePagingAdapter()
                    adapter = adapterPaging
                    lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.CREATED) {
                            viewModel.serials.collectLatest(adapterPaging::submitData)
                        }
                    }
                }
            }


        }


    }


}