package com.example.skillcinema.ui.pages.filter_page

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.skillcinema.R
import com.example.skillcinema.databinding.FragmentFilterBinding
import com.example.skillcinema.domain.Constants
import com.example.skillcinema.ui.helpers.Helper
import com.example.skillcinema.ui.helpers.Navigator
import java.util.*

class FilterFragment : Fragment() {

    private lateinit var vb: FragmentFilterBinding
    private val viewModel: FilterViewModel by activityViewModels()

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

        header.apply {
            btnBack.setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
            headerTitle.text = "Настройки поиска"
        }

        filmType.apply {

            val listOfConstants = listOf(Constants.ALL, Constants.FILM, Constants.TV_SERIES)
            val listOfTxt = listOf(tv1, tv2, tv3)
            val listOfBtn = listOf(btnAll, btnFilms, btnSerials)

            viewModel.getType()

            for (i in listOfConstants.indices) {
                if (listOfConstants[i] == viewModel.type) {
                    selectedBtn(listOfBtn[i], listOfTxt[i], listOfConstants[i], false)
                } else {
                    unSelectedBtn(listOfBtn[i], listOfTxt[i])
                }
            }

            btnAll.setOnClickListener {
                unSelectedBtn(btnFilms, tv2)
                unSelectedBtn(btnSerials, tv3)
                selectedBtn(btnAll, tv1, Constants.ALL, false)
            }

            btnFilms.setOnClickListener {
                unSelectedBtn(btnAll, tv1)
                unSelectedBtn(btnSerials, tv3)
                selectedBtn(btnFilms, tv2, Constants.FILM, false)
            }

            btnSerials.setOnClickListener {
                unSelectedBtn(btnAll, tv1)
                unSelectedBtn(btnFilms, tv2)
                selectedBtn(btnSerials, tv3, Constants.TV_SERIES, false)
            }

        }

        country.setOnClickListener {
            Navigator.actionFilterFragmentToCountryAndGenreFilterFragment(
                findNavController(),
                Constants.COUNTRY
            )
        }

        countryTv.text = viewModel.country

        genre.setOnClickListener {
            Navigator.actionFilterFragmentToCountryAndGenreFilterFragment(
                findNavController(),
                Constants.GENRE
            )
        }

        genreTv.text = viewModel.genre

        year.setOnClickListener {
            Navigator.actionFilterFragmentToYearPickerFragment(findNavController())
        }

        yearTv.text = viewModel.year

        seekBar.apply {
            valueFrom = 1f
            valueTo = 10f
            stepSize = 1f
            values = viewModel.rating
        }

        seekBar.setLabelFormatter { value ->
            Helper.setToast(requireContext(), viewModel.type)
            String.format(Locale.getDefault(), "%.0f", value)
        }

        rateTv.apply {
            text = "любой"
            setOnClickListener {
                seekBar.values = listOf(1f, 10f)
            }
        }

        sortType.apply {

            typeName.text = "Сортировать"
            tv1.text = "Дата"
            tv2.text = "Популярность"
            tv3.text = "Рейтинг"

            val listOfConstants = listOf(Constants.RATING, Constants.NUM_VOTE, Constants.YEAR)
            val listOfTxt = listOf(tv1, tv2, tv3)
            val listOfBtn = listOf(btnAll, btnFilms, btnSerials)

            for (i in listOfConstants.indices) {
                if (listOfConstants[i] == viewModel.order) {
                    selectedBtn(listOfBtn[i], listOfTxt[i], listOfConstants[i], true)
                } else {
                    unSelectedBtn(listOfBtn[i], listOfTxt[i])
                }
            }

            btnAll.setOnClickListener {
                unSelectedBtn(btnFilms, tv2)
                unSelectedBtn(btnSerials, tv3)
                selectedBtn(btnAll, tv1, Constants.RATING, true)
            }

            btnFilms.setOnClickListener {
                unSelectedBtn(btnAll, tv1)
                unSelectedBtn(btnSerials, tv3)
                selectedBtn(btnFilms, tv2, Constants.NUM_VOTE, true)
            }

            btnSerials.setOnClickListener {
                unSelectedBtn(btnAll, tv1)
                unSelectedBtn(btnFilms, tv2)
                selectedBtn(btnSerials, tv3, Constants.YEAR, true)
            }


        }

    }

    private fun selectedBtn(
        btn: ConstraintLayout,
        txt: TextView,
        type: String?,
        isOrder: Boolean,
    ) {
        btn.setBackgroundResource(R.color.main)
        txt.setTextColor(Helper.setColor(requireContext(), R.color.white))
        if (isOrder) viewModel.saveOrder(type) else viewModel.saveType(type)
    }

    private fun unSelectedBtn(btn: ConstraintLayout, txt: TextView) {
        btn.setBackgroundResource(R.color.white)
        txt.setTextColor(Helper.setColor(requireContext(), R.color.black))
    }

    override fun onStop() {
        viewModel.saveRateTo(vb.seekBar.values[0].toString())
        viewModel.saveRateFrom(vb.seekBar.values[1].toString())
        super.onStop()
    }


}