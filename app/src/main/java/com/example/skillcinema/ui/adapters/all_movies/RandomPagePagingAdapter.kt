package com.example.skillcinema.ui.adapters.all_movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillcinema.databinding.ItemMoviesBinding
import com.example.skillcinema.databinding.ItemMoviesGridBinding
import com.example.skillcinema.model.data.apiFilms.Item
import com.example.skillcinema.model.data.apiTop.Film

class RandomPagePagingAdapter :
    PagingDataAdapter<Item, RandomPagePagingAdapter.TasksVH>(DataDiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksVH {
        val binding =
            ItemMoviesGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TasksVH(binding)
    }

    override fun onBindViewHolder(holder: TasksVH, position: Int) {
        val task = getItem(position)
        holder.initView(task)
    }

    inner class TasksVH(private val binding: ItemMoviesGridBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun initView(movie: Item?) = with(binding) {

            Glide.with(itemView.context)
                .load(movie?.posterUrlPreview)
                .into(imageView)

            movieName.text = movie?.nameRu
            movieType.text = movie!!.genres[0].genre
            rate.text = movie.ratingImdb.toString()

        }

    }


    private object DataDiffUtilCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.kinopoiskId == newItem.kinopoiskId
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }


    }
}



