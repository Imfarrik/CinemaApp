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



        viewModel.singleStaff.observe(viewLifecycleOwner) { singleStaff ->

            btnFilmography.setOnClickListener {
                Navigator.actionActorFragmentToFilmographyFragment(findNavController(), singleStaff)
            }

            Glide.with(requireContext())
                .load(singleStaff.posterUrl)
                .into(imageView)

            actorName.text = singleStaff.nameRu ?: singleStaff.nameEn
            typeNew.text = singleStaff.profession

            bestOfTheBest.apply {
                count.text = "Все"
                type.text = "Лучшее"
                allBtn.setOnClickListener {
                    Navigator.actionActorFragmentToAllMoviesFragment(
                        findNavController(),
                        singleStaff
                    )
                }
                rv.apply {
                    layoutManager = LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                    adapter =
                        ActorFilmListAdapter(singleStaff.films, viewModel.repository, { movie_id ->
                            Navigator.actionActorFragmentToSingleMovieFragment(
                                findNavController(),
                                movie_id
                            )
                        }, {
                            Navigator.actionActorFragmentToAllMoviesFragment(
                                findNavController(),
                                singleStaff
                            )
                        })
                }
            }

            countTv.text = singleStaff.films.size.toString() + " фильма"


        }

    }


}