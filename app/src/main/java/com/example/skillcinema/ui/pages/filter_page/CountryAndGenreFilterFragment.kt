package com.example.skillcinema.ui.pages.filter_page

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skillcinema.databinding.FragmentCountryAndGenreFilterBinding
import com.example.skillcinema.domain.Constants
import com.example.skillcinema.ui.adapters.filter_adapter.FilterAdapter
import com.example.skillcinema.ui.helpers.Helper


class CountryAndGenreFilterFragment : Fragment() {

    private lateinit var vb: FragmentCountryAndGenreFilterBinding
    private val vm: FilterViewModel by activityViewModels()
    private val args: CountryAndGenreFilterFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        vb = FragmentCountryAndGenreFilterBinding.inflate(layoutInflater)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Helper.insets(vb.root)

        initView()
    }

    private fun initView() = with(vb) {

        header.headerTitle.text = args.name
        header.btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        rv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        if (args.name == Constants.COUNTRY) {

            editQuery.hint = "Введите страну"

            editQuery.addTextChangedListener(vm.countryTxtWatcher)

            vm.countryList.observe(viewLifecycleOwner) {
                rv.adapter = FilterAdapter(country = it, isCountry = true) { country ->
                    vm.saveCountry(country)
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }
            }

        } else {

            editQuery.hint = "Введите жанр"

            editQuery.addTextChangedListener(vm.genreTxtWatcher)

            vm.genreList.observe(viewLifecycleOwner) {
                rv.adapter = FilterAdapter(genre = it, isCountry = false) { genre ->
                    vm.saveGenre(genre)
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }
            }

        }

    }


}