package com.example.skillcinema.ui.adapters.home_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillcinema.databinding.ItemMoviesBinding

import com.example.skillcinema.model.data.apiNew.Item

class MovieAdapter(
    private val data: List<Item>,
    private val selectListener: () -> Unit
) :
    RecyclerView.Adapter<MovieAdapter.MovieVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVH {
        val binding = ItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieVH(binding)
    }

    override fun onBindViewHolder(holder: MovieVH, position: Int) {
        val task = data[position]
        holder.initView(task, position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class MovieVH(private val binding: ItemMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun initView(item: Item, pos: Int) = with(binding) {

            Glide.with(itemView.context)
                .load(item.posterUrlPreview)
                .into(imageView)

            movieName.text = item.nameRu
            movieType.text = item.genres[0].genre


        }


    }
}