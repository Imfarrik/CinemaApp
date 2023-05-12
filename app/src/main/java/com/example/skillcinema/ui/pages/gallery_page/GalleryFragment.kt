package com.example.skillcinema.ui.pages.gallery_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.skillcinema.databinding.FragmentGalleryBinding
import com.example.skillcinema.ui.helpers.Helper
import com.example.skillcinema.ui.helpers.Navigator
import com.example.skillcinema.ui.adapters.gallery_adapter.ImagePagingAdapter
import com.example.skillcinema.ui.adapters.gallery_adapter.TypeAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GalleryFragment : Fragment() {

    private lateinit var vb: FragmentGalleryBinding

    private val viewModel: GalleryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        vb = FragmentGalleryBinding.inflate(layoutInflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Helper.insets(vb.root)

        initView()
    }

    private fun initView() = with(vb) {

        header.headerTitle.text = "Галерия"

        header.btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        typeRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        photoRv.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        viewModel.listSize.observe(viewLifecycleOwner) {

            typeRv.adapter = TypeAdapter(it) { pos ->

                val adapterPaging = ImagePagingAdapter() { position ->
                    Navigator.startImageActivity(requireContext(), typeId = pos, imageId = position)
                }

                photoRv.adapter = adapterPaging

                lifecycleScope.launch {

                    repeatOnLifecycle(Lifecycle.State.CREATED) {

                        viewModel.listPaging[pos].collectLatest(adapterPaging::submitData)

                    }

                }

            }

        }


    }


}