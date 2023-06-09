package com.example.skillcinema.ui.adapters.single_page_adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillcinema.databinding.BtnAllMoviesBinding
import com.example.skillcinema.databinding.ItemMoviesBinding
import com.example.skillcinema.domain.Constants.ALL_ITEMS_VIEW_TYPE
import com.example.skillcinema.domain.Constants.DEFAULT_VIEW_TYPE
import com.example.skillcinema.domain.Constants.MAX_ITEMS
import com.example.skillcinema.model.data.apiSimilars.Item


class SimilarListAdapter(private val movies: List<Item>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return if (movies.size >= MAX_ITEMS) {
            MAX_ITEMS + 1
        } else {
            movies.size
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == MAX_ITEMS) {
            ALL_ITEMS_VIEW_TYPE
        } else {
            DEFAULT_VIEW_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ALL_ITEMS_VIEW_TYPE) {
            val binding =
                BtnAllMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            AllMoviesViewHolder(binding)
        } else {
            val binding =
                ItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            MovieViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieViewHolder) {
            holder.bind(movies[position])
        }
    }

    inner class MovieViewHolder(private val binding: ItemMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Item) = with(binding) {

            rateContainer.isVisible = false

            Glide.with(itemView.context)
                .load(movie.posterUrlPreview)
                .into(imageView)

            movieName.text = movie.nameRu ?: movie.nameOriginal
            movieType.text = movie.nameOriginal ?: movie.nameEn
        }
    }

    inner class AllMoviesViewHolder(binding: BtnAllMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnAll.setOnClickListener {
                Toast.makeText(itemView.context, "Hello", Toast.LENGTH_SHORT).show()
            }
        }

    }

}