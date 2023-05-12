package com.example.skillcinema.ui.pages.actor_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.skillcinema.databinding.FragmentActorBinding
import com.example.skillcinema.ui.helpers.Helper
import com.example.skillcinema.ui.helpers.Navigator
import com.example.skillcinema.ui.adapters.actor_page_adapter.ActorFilmListAdapter

class ActorFragment : Fragment() {

    private lateinit var vb: FragmentActorBinding
    private val viewModel: ActorViewModel by viewModels()
    private val args: ActorFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        vb = FragmentActorBinding.inflate(inflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Helper.insets(vb.root)

        initView()
    }

    private fun initView() = with(vb) {

        viewModel.getSingleStaff(args.staffId)

        btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        viewModel.singleStaff.observe(viewLifecycleOwner) {

            Glide.with(requireContext())
                .load(it.posterUrl)
                .into(imageView)

            actorName.text = it.nameRu ?: it.nameEn
            typeNew.text = it.profession

            bestOfTheBest.apply {
                count.text = "Все"
                type.text = "Лучшее"
                allBtn.setOnClickListener {

                }
                rv.apply {
                    layoutManager = LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                    adapter = ActorFilmListAdapter(it.films, viewModel.repository, { movie_id ->
                        Navigator.actionActorFragmentToSingleMovieFragment(
                            findNavController(),
                            movie_id
                        )
                    }, {

                    })
                }
            }

            countTv.text = it.films.size.toString() + " фильма"


        }

    }


}