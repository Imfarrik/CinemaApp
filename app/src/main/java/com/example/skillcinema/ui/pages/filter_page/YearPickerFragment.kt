package com.example.skillcinema.ui.pages.filter_page

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.skillcinema.R
import com.example.skillcinema.databinding.FragmentYearPickerBinding
import com.example.skillcinema.ui.helpers.Helper

class YearPickerFragment : Fragment() {

    private lateinit var vb: FragmentYearPickerBinding
    private val vm: FilterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        vb = FragmentYearPickerBinding.inflate(layoutInflater)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Helper.insets(vb.root)

        initView()
    }

    private fun initView() = with(vb) {

        header.headerTitle.text = "Период"
        header.btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        saveBtn.setOnClickListener {
            vm.savePeriodFrom(fromYearToYear.currentSelectYear)
            vm.savePeriodTo(toYearToYear.currentSelectYear)
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }


    }


}