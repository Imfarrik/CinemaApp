package com.example.skillcinema.ui.adapters.gallery_adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillcinema.R
import com.example.skillcinema.databinding.ItemGalleryBinding
import com.example.skillcinema.databinding.ItemTypeBinding
import com.example.skillcinema.model.data.apiImages.Item

class TypeAdapter(
    private val listSize: MutableList<String>,
    private val clickListener: (pos: Int) -> Unit,
) :
    RecyclerView.Adapter<TypeAdapter.TypeVH>() {

    private var singleItemSelectionPosition = 0

    private val data =
        listOf(
            "Кадры",
            "Изображения со съемок",
            "Постеры",
            "Фан-арты",
            "Промо",
            "Концепт-арты",
            "Обои",
            "Обложки",
            "Скриншоты"
        )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeVH {
        val binding = ItemTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TypeVH(binding)
    }

    override fun onBindViewHolder(holder: TypeVH, position: Int) {

        val image = data[position]
        val size = listSize[position]
        holder.initView(image, position, size)

    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class TypeVH(private val binding: ItemTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun initView(item: String, pos: Int, size: String) = with(binding) {

            typeCount.text = size

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

            typeName.text = item


        }

        private fun setSingleSelection() {
            if (absoluteAdapterPosition == RecyclerView.NO_POSITION) return
            notifyItemChanged(singleItemSelectionPosition)
            singleItemSelectionPosition = absoluteAdapterPosition
            notifyItemChanged(singleItemSelectionPosition)
        }


    }
}