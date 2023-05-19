package com.example.skillcinema.ui.pages.profile_page

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skillcinema.R
import com.example.skillcinema.databinding.FragmentProfileBinding
import com.example.skillcinema.ui.adapters.profile_adapter.CollectionAdapter
import com.example.skillcinema.ui.adapters.profile_adapter.InterestedAdapter
import com.example.skillcinema.ui.adapters.profile_adapter.WatchedAdapter
import com.example.skillcinema.ui.helpers.Helper
import com.example.skillcinema.ui.helpers.Navigator
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class ProfileFragment : Fragment() {

    private lateinit var vb: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        vb = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Helper.insets(vb.root)

        initView()

    }

    private fun initView() = with(vb) {

        watchedMovie.apply {
            holderType.text = "Просмотрено"
            btnAll.setOnClickListener {

            }
            viewModel.allWatched.observe(viewLifecycleOwner) {

                btnAll.text = "${it.size}  >"

                btnAll.setOnClickListener {
                    Navigator.actionProfileFragmentToAllMoviesCollectionFragment(
                        findNavController(),
                        "1"
                    )
                }

                rvMovie.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                rvMovie.adapter = WatchedAdapter(it, { movie_id ->
                    Navigator.actionProfileFragmentToSingleMovieFragment(
                        findNavController(),
                        movie_id
                    )
                }, {
                    Navigator.actionProfileFragmentToAllMoviesCollectionFragment(
                        findNavController(),
                        "1"
                    )
                })
            }
        }

        rvCollections.layoutManager = GridLayoutManager(requireContext(), 2)
        viewModel.allCollection.observe(viewLifecycleOwner) {
            rvCollections.adapter = CollectionAdapter(viewModel.appDatabase, it) {

            }
        }
        btnNewCollection.setOnClickListener {
            showNewDialog()
        }

        interestedMovie.apply {
            holderType.text = "Вам было интересно"
            btnAll.setOnClickListener {

            }
            viewModel.allInterested.observe(viewLifecycleOwner) {

                btnAll.text = "${it.size}  >"

                btnAll.setOnClickListener {
                    Navigator.actionProfileFragmentToAllMoviesCollectionFragment(
                        findNavController(),
                        "2"
                    )
                }

                rvMovie.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                rvMovie.adapter = InterestedAdapter(it, { movie_id ->
                    Navigator.actionProfileFragmentToSingleMovieFragment(
                        findNavController(),
                        movie_id
                    )
                }, {
                    Navigator.actionProfileFragmentToAllMoviesCollectionFragment(
                        findNavController(),
                        "2"
                    )
                })
            }
        }

    }

    private fun showNewDialog() {
        val dialog = Dialog(requireContext())
        dialog.apply {
            setContentView(R.layout.dialog_new_collection)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        val btnX = dialog.findViewById<ImageView>(R.id.btn_x)
        val btnDone = dialog.findViewById<MaterialButton>(R.id.btn_done)
        val editTv = dialog.findViewById<TextInputEditText>(R.id.edit_tv)

        btnX.setOnClickListener {
            dialog.cancel()
        }
        btnDone.setOnClickListener {
            if (editTv.text!!.isNotEmpty()) {
                val newCollectionName = editTv.text.toString()
                viewModel.insertNewCollection(newCollectionName)
                dialog.cancel()
            } else {
                Helper.setToast(requireContext(), "Название не может быть пустым")
            }
        }

        dialog.setOnDismissListener {
            viewModel.getAll()
        }

        dialog.show()

    }


}