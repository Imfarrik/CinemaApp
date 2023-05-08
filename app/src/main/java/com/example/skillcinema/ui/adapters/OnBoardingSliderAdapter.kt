package com.example.skillcinema.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skillcinema.R

class OnBoardingSliderAdapter :
    RecyclerView.Adapter<OnBoardingSliderAdapter.OnBoardingViewHolder>() {

    val list = listOf(
        OnBoardingModel(R.drawable.on_boarding_1, "Узнавай\nо премьерах"),
        OnBoardingModel(R.drawable.on_boarding_2, "Создавай\nколлекции"),
        OnBoardingModel(R.drawable.on_boarding_3, "Делись\nс друзьями"),
    )

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder =
        OnBoardingViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.on_boarding_item, parent, false)
        )

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {

        val item = list[position]

        holder.init(item)

    }

    inner class OnBoardingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun init(item: OnBoardingModel) {

            val imageView: ImageView = itemView.findViewById(R.id.imageView)
            val textView: TextView = itemView.findViewById(R.id.tv_description)

            imageView.setImageResource(item.res)
            textView.text = item.description

        }
    }
}

data class OnBoardingModel(val res: Int, val description: String)