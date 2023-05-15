package com.example.skillcinema.ui.pages.search_page

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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

        editQuery.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().trim()

                viewModel.searchByKeyWord(query)


            }
        })

        viewModel.result.observe(viewLifecycleOwner) {
            resultRv.layoutManager = GridLayoutManager(requireContext(), 3)
            resultRv.adapter = SearchAdapter(it.items)
        }

    }


}