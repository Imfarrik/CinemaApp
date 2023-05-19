package com.example.skillcinema.ui.adapters.filter_adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.skillcinema.R
import com.example.skillcinema.databinding.ItemFilterBinding
import com.example.skillcinema.model.data.apiFilter.Country
import com.example.skillcinema.model.data.apiFilter.Genre
import com.example.skillcinema.model.data.apiSingleStaff.Film

class FilterAdapter(
    private val country: List<Country>? = null,
    private val genre: List<Genre>? = null,
    private val isCountry: Boolean,
    private val clickListener: (id: String) -> Unit,
) :
    RecyclerView.Adapter<FilterAdapter.TypeVH>() {

    private var singleItemSelectionPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeVH {
        val binding = ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TypeVH(binding)
    }

    override fun onBindViewHolder(holder: TypeVH, position: Int) {

        if (isCountry) {
            val country = country!![position]
            holder.initCountry(country, position)
        } else {
            val genre = genre!![position]
            holder.initGenre(genre, position)
        }

    }

    override fun getItemCount(): Int {
        return if (isCountry) country!!.size else genre!!.size
    }

    inner class TypeVH(private val binding: ItemFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun initCountry(item: Country, pos: Int) = with(binding) {

            itemView.setOnClickListener {
                setSingleSelection()
            }

            if (singleItemSelectionPosition == pos) {
                container.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.light_gray
                    )
                )
                clickListener("${item.id},${item.country}")
            } else {
                container.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.white
                    )
                )
            }

            tvCountry.text = item.country
        }

        fun initGenre(item: Genre, pos: Int) = with(binding) {
            itemView.setOnClickListener {
                setSingleSelection()
            }

            if (singleItemSelectionPosition == pos) {
                container.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.light_gray
                    )
                )
                clickListener("${item.id},${item.genre}")
            } else {
                container.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.white
                    )
                )
            }

            tvCountry.text = item.genre

        }

        private fun setSingleSelection() {
            if (absoluteAdapterPosition == RecyclerView.NO_POSITION) return
            notifyItemChanged(singleItemSelectionPosition)
            singleItemSelectionPosition = absoluteAdapterPosition
            notifyItemChanged(singleItemSelectionPosition)
        }
    }
}