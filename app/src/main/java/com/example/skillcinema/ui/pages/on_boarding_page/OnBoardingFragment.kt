package com.example.skillcinema.ui.pages.on_boarding_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.skillcinema.App
import com.example.skillcinema.R
import com.example.skillcinema.databinding.FragmentOnBoardingBinding
import com.example.skillcinema.domain.SharedPreferencesManager
import com.example.skillcinema.ui.helpers.Helper.insets
import com.example.skillcinema.ui.helpers.Navigator
import com.example.skillcinema.ui.adapters.on_boarding_adapter.OnBoardingSliderAdapter
import javax.inject.Inject

class OnBoardingFragment : Fragment() {

    private lateinit var vb: FragmentOnBoardingBinding
    private val imageView = mutableListOf<ImageView>()

    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    init {
        App.getAppComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vb = FragmentOnBoardingBinding.inflate(layoutInflater)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        insets(vb.root)

        initView()

    }

    private fun initView() = with(vb) {

        viewPager.adapter = OnBoardingSliderAdapter()
        (viewPager.getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        initIndicators()

        vb.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setIndicators(position)
            }
        })

        btnMissing.setOnClickListener {
            sharedPreferencesManager.isWasOpened(true)
            Navigator.startHomeActivity(requireActivity())
        }

    }

    private fun initIndicators() = with(vb) {

        for (i in 0 until OnBoardingSliderAdapter().list.size) {
            imageView.add(ImageView(requireContext()))
        }

        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        layoutParams.setMargins(8, 0, 8, 0)

        for (i in imageView.indices) {
            val image = imageView[i]
            image.setImageResource(R.drawable.on_board_indicator_inactive)
            image.layoutParams = layoutParams
            indicator.addView(image)
        }


    }

    private fun setIndicators(index: Int) {
        for (i in 0 until imageView.size) {
            val imageView1 = vb.indicator.getChildAt(i) as ImageView
            if (i == index) {
                imageView1.setImageResource(R.drawable.on_board_indicator_active)
            } else {
                imageView1.setImageResource(R.drawable.on_board_indicator_inactive)
            }
        }
    }

}