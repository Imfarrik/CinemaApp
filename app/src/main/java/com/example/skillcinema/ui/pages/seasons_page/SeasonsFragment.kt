package com.example.skillcinema.ui.pages.seasons_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skillcinema.databinding.FragmentSeasonsBinding
import com.example.skillcinema.model.data.apiSeason.ApiSeason
import com.example.skillcinema.ui.adapters.seasons_adapter.SeasonsAdapter
import com.example.skillcinema.ui.adapters.seasons_adapter.TypeAdapter
import com.example.skillcinema.ui.helpers.Helper
import com.google.gson.Gson

class SeasonsFragment : Fragment() {

    private lateinit var vb: FragmentSeasonsBinding
    private val viewModel: SeasonsViewModel by viewModels()
    private val args: SeasonsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        vb = FragmentSeasonsBinding.inflate(layoutInflater)
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

        val arg = Gson().fromJson(args.seasons, ApiSeason::class.java)

        header.headerTitle.text = "Все серии"

        typeRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        typeRv.adapter = TypeAdapter(arg) { pos ->

            episodeTitle.text = "${pos + 1} сезон, ${arg.items[pos].episodes.size} серий "

            episodeRv.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            episodeRv.adapter = SeasonsAdapter(arg.items[pos].episodes)

        }

    }


}