package com.example.skillcinema.ui.adapters.all_movies

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.WindowId
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillcinema.R
import com.example.skillcinema.databinding.ItemGalleryBinding
import com.example.skillcinema.databinding.ItemMoviesBinding
import com.example.skillcinema.databinding.ItemMoviesGridBinding
import com.example.skillcinema.databinding.ItemTypeBinding
import com.example.skillcinema.domain.Constants
import com.example.skillcinema.model.data.apiImages.Item
import com.example.skillcinema.model.data.apiSingleStaff.ApiSingleStaff
import com.example.skillcinema.model.data.apiSingleStaff.Film
import com.example.skillcinema.model.data.db.WatchedHolder
import com.example.skillcinema.model.repository.Repository

class WatchedAdapter(
    private val movies: List<WatchedHolder>,
    private val clickListener: (id: Int) -> Unit,
) :
    RecyclerView.Adapter<WatchedAdapter.TypeVH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeVH {
        val binding =
            ItemMoviesGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TypeVH(binding)
    }

    override fun onBindViewHolder(holder: TypeVH, position: Int) {

        val movie = movies[position]
        holder.initView(movie, position)

    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class TypeVH(private val binding: ItemMoviesGridBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun initView(item: WatchedHolder, pos: Int) = with(binding) {

            itemView.setOnClickListener {
                clickListener(item.movie_id)
            }

            Glide.with(itemView.context)
                .load(item.image_url)
                .into(imageView)

            movieName.text = item.name_ru ?: item.name_en
            movieType.text = item.genre
            rate.text = item.raiting.toString()

        }


    }
}