package com.example.skillcinema.ui.adapters.single_page_adapters

import android.app.ActionBar.LayoutParams
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillcinema.databinding.ItemGalleryBinding
import com.example.skillcinema.model.data.apiImages.Item

class ImagesAdapter(
    private val data: List<Item>,
    private val clickListener: (pos: Int) -> Unit,
) :
    RecyclerView.Adapter<ImagesAdapter.StaffVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffVH {
        val binding = ItemGalleryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StaffVH(binding)
    }

    override fun onBindViewHolder(holder: StaffVH, position: Int) {

        val image = data[position]
        holder.initView(image, position)

    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class StaffVH(private val binding: ItemGalleryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun initView(item: Item, pos: Int) = with(binding) {
            val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            container.setPadding(0, 0, 16, 0)
            container.layoutParams = params
            Glide.with(itemView.context)
                .load(item.previewUrl)
                .into(imageView)

            itemView.setOnClickListener {
                clickListener(pos)
            }

        }


    }
}