package com.example.skillcinema.ui.custom_view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skillcinema.R
import com.example.skillcinema.databinding.BottomSheetErrorBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetError : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetErrorBinding

    override fun getTheme(): Int = R.style.BottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = BottomSheetErrorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnClose.setOnClickListener {
            this@BottomSheetError.dismiss()
        }


    }

    companion object {
        const val TAG = "hello"


//        val bottomSheet = BottomSheet(apiSingleMovie)
//        val fragmentManager = (activity as FragmentActivity).supportFragmentManager
//        bottomSheet.dismissListener {
//            viewModel.getDBData()
//            initView()
//        }
//        fragmentManager.let { bottomSheet.show(it, BottomSheet.TAG) }
    }

}