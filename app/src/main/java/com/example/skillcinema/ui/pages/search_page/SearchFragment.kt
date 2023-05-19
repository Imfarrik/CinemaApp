package com.example.skillcinema.ui.pages.search_page

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.skillcinema.databinding.FragmentSearchBinding
import com.example.skillcinema.domain.Constants
import com.example.skillcinema.ui.adapters.search_adapter.SearchAdapter
import com.example.skillcinema.ui.helpers.Helper
import com.example.skillcinema.ui.helpers.Navigator

class SearchFragment : Fragment() {

    private lateinit var vb: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        vb = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Helper.insets(vb.root)

        initView()

    }

    private fun initView() = with(vb) {

        btnFilter.setOnClickListener {
            Navigator.startFilterActivity(requireContext())
        }

        editQuery.addTextChangedListener(viewModel.textChangeListener)

        viewModel.isEmptyList.observe(viewLifecycleOwner) {
            isEmptyTv.isVisible = it
            resultRv.isVisible = !it
        }

        viewModel.result.observe(viewLifecycleOwner) {
            resultRv.layoutManager = GridLayoutManager(requireContext(), 3)
            resultRv.adapter = SearchAdapter(it.items) { id ->
                Navigator.actionSearchFragmentToSingleMovieFragment(findNavController(), id)
            }
        }

    }


    override fun onResume() {
        super.onResume()
        viewModel.search()
    }


}