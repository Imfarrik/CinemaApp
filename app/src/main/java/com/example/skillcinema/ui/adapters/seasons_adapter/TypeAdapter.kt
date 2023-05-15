package com.example.skillcinema.ui.adapters.seasons_adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.skillcinema.R
import com.example.skillcinema.databinding.ItemTypeBinding
import com.example.skillcinema.model.data.apiSeason.ApiSeason
import com.example.skillcinema.model.data.apiSeason.Item

class TypeAdapter(
    private val data: ApiSeason,
    private val clickListener: (pos: Int) -> Unit,
) :
    RecyclerView.Adapter<TypeAdapter.TypeVH>() {

    private var singleItemSelectionPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeVH {
        val binding = ItemTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TypeVH(binding)
    }

    override fun onBindViewHolder(holder: TypeVH, position: Int) {

        val item = data.items[position]
        holder.initView(item, position)

    }

    override fun getItemCount(): Int {
        return data.items.size
    }

    inner class TypeVH(private val binding: ItemTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun initView(item: Item, pos: Int) = with(binding) {

            typeCount.isVisible = false

            itemView.setOnClickListener {
                setSingleSelection()
            }

            if (singleItemSelectionPosition == pos) {
                cardView.setCardBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.main
                    )
                )
                cardView.strokeWidth = 0
                typeName.setTextColor(Color.WHITE)
                clickListener(pos)
            } else {
                cardView.strokeWidth = 1
                cardView.setCardBackgroundColor(Color.WHITE)
                typeName.setTextColor(Color.BLACK)
            }

            typeName.text = (pos + 1).toString()
            typeName.setPadding(0, 0, 0, 0)

        }

        private fun setSingleSelection() {
            if (absoluteAdapterPosition == RecyclerView.NO_POSITION) return
            notifyItemChanged(singleItemSelectionPosition)
            singleItemSelectionPosition = absoluteAdapterPosition
            notifyItemChanged(singleItemSelectionPosition)
        }


    }
}