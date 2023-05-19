package com.example.skillcinema.ui.adapters.search_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillcinema.databinding.ItemMoviesBinding
import com.example.skillcinema.databinding.ItemMoviesGridBinding
import com.example.skillcinema.model.data.apiFilms.Item

class SearchAdapter(
    private val data: List<Item>,
    private val clickListener: (Int) -> Unit,
) :
    RecyclerView.Adapter<SearchAdapter.TypeVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeVH {
        val binding =
            ItemMoviesGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TypeVH(binding)
    }

    override fun onBindViewHolder(holder: TypeVH, position: Int) {

        if (data.isNotEmpty()) {
            val item = data[position]
            holder.initView(item)
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class TypeVH(private val binding: ItemMoviesGridBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun initView(item: Item) = with(binding) {

            itemView.setOnClickListener {
                clickListener(item.kinopoiskId)
            }

            Glide.with(itemView.context)
                .load(item.posterUrlPreview)
                .into(imageView)

            rate.text = item.ratingImdb.toString()

            movieName.text = item.nameRu ?: item.nameEn
            movieType.text =
                "${item.year}, ${if (item.genres.isNotEmpty()) item.genres.first().genre else ""}"

        }

    }


}