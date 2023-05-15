package com.example.skillcinema.ui.adapters.search_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillcinema.databinding.ItemMoviesBinding
import com.example.skillcinema.model.data.apiFilms.Item

class SearchAdapter(
    private val data: List<Item>,
) :
    RecyclerView.Adapter<SearchAdapter.TypeVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeVH {
        val binding =
            ItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TypeVH(binding)
    }

    override fun onBindViewHolder(holder: TypeVH, position: Int) {

        val item = data[position]
        holder.initView(item)

    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class TypeVH(private val binding: ItemMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun initView(item: Item) = with(binding) {

            Glide.with(itemView.context)
                .load(item.posterUrlPreview)
                .into(imageView)

            rate.text = item.ratingImdb.toString()

            movieName.text = item.nameRu ?: item.nameEn
            movieType.text = "${item.year}, ${item.genres[0].genre}"

        }

    }


}