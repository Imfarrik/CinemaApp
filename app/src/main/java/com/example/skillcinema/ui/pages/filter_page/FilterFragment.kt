package com.example.skillcinema.ui.pages.filter_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.skillcinema.databinding.FragmentFilterBinding
import com.example.skillcinema.ui.helpers.Helper

class FilterFragment : Fragment() {

    private lateinit var vb: FragmentFilterBinding
    private val viewModel: FilterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        vb = FragmentFilterBinding.inflate(layoutInflater)
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

        header.headerTitle.text = "Настройки поиска"

        seekBar.valueFrom = 1f
        seekBar.valueTo = 10f


    }


}