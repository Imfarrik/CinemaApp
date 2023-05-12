package com.example.skillcinema.ui.adapters.single_page_adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillcinema.databinding.ItemStaffBinding
import com.example.skillcinema.domain.Constants

import com.example.skillcinema.model.data.apiStaff.ApiStaffItem
import com.google.gson.annotations.Until

class StaffAdapter(
    data: ArrayList<ApiStaffItem>,
    selectListener: (actors: Int, others: Int) -> Unit,
    private val clickListener: (Int) -> Unit,
    private val isActors: Boolean = true,
) :
    RecyclerView.Adapter<StaffAdapter.StaffVH>() {

    private val actors = mutableListOf<ApiStaffItem>()
    private val others = mutableListOf<ApiStaffItem>()

    init {

        data.forEach {
            if (it.professionKey == Constants.ACTOR) {
                actors.add(it)
            } else {
                others.add(it)
            }
        }

        selectListener(actors.size, others.size)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffVH {
        val binding = ItemStaffBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StaffVH(binding)
    }

    override fun onBindViewHolder(holder: StaffVH, position: Int) {
        if (isActors) {
            val task = actors[position]
            holder.initView(task)
        } else {
            val task = others[position]
            holder.initView(task)
        }

    }

    override fun getItemCount(): Int {
        return if (isActors) actors.size else others.size
    }

    inner class StaffVH(private val binding: ItemStaffBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun initView(item: ApiStaffItem) = with(binding) {

            itemView.setOnClickListener {
                clickListener(item.staffId)
            }

            Glide.with(itemView.context)
                .load(item.posterUrl)
                .into(imageView)

            actorName.text = "${item.nameRu}\n${item.nameEn}"
            actorMovieName.text = item.description


        }


    }
}