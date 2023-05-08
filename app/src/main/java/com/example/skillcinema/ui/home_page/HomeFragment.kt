package com.example.skillcinema.ui.home_page

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skillcinema.databinding.FragmentHomeBinding
import com.example.skillcinema.model.repository.State
import com.example.skillcinema.ui.Helper
import com.example.skillcinema.ui.adapters.home_adapter.FilmListAdapter
import com.example.skillcinema.ui.adapters.home_adapter.NewListAdapter
import com.example.skillcinema.ui.adapters.home_adapter.TopListAdapter
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var vb: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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

        val types =
            listOf("Примьеры", "Популярное", "-", "Топ-250", "Сериалы")

        val viewHolders = listOf(
            newMovie,
            popular,
            USA,
            top,
            serials
        )

        for (i in viewHolders.indices) {
            viewHolders[i].apply {
                holderType.text = types[i]
                rvMovie.layoutManager =
                    LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                btnAll.setOnClickListener {
                    findNavController().navigate(
                        HomeFragmentDirections.actionHomeFragmentToAllMoviesFragment(
                            holderType.text as String
                        )
                    )
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    when (it) {
                        State.Loading -> {
                            nestedScrollView.isVisible = false
                            loader.isVisible = true
                        }
                        State.Success -> {
                            loader.isVisible = false
                            nestedScrollView.isVisible = true
                        }
                        is State.ServerError -> {
                            Helper.setToast(requireContext(), it.message)
                        }
                        else -> {}
                    }
                }
            }
        }

        viewModel.newMovies.observe(viewLifecycleOwner) {
            viewHolders[0].rvMovie.adapter = NewListAdapter(it.items)
        }

        viewModel.topMovies.observe(viewLifecycleOwner) {
            viewHolders[1].rvMovie.adapter = TopListAdapter(it.films)
        }

        viewModel.randomName.observe(viewLifecycleOwner) {
            viewHolders[2].holderType.text = it
        }

        viewModel.randomMovies.observe(viewLifecycleOwner) {
            viewHolders[2].rvMovie.adapter = FilmListAdapter(it.items)
        }

        viewModel.top250Movies.observe(viewLifecycleOwner) {
            viewHolders[3].rvMovie.adapter = TopListAdapter(it.films)
        }

        viewModel.topSeries.observe(viewLifecycleOwner) {
            viewHolders[4].rvMovie.adapter = FilmListAdapter(it.items)
        }


    }


}