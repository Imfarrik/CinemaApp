package com.example.skillcinema.ui.adapters.filmography_page_adapter

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
import com.example.skillcinema.model.repository.Repository

class MovieAdapter(
    private val repository: Repository,
    private val movies: List<Film>,
    private val clickListener: (id: Int) -> Unit,
) :
    RecyclerView.Adapter<MovieAdapter.TypeVH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeVH {
        val binding = ItemMoviesGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TypeVH(binding)
    }

    override fun onBindViewHolder(holder: TypeVH, position: Int) {

        val movie = movies[position]
        holder.initView(movie)

    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class TypeVH(private val binding: ItemMoviesGridBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun initView(item: Film) = with(binding) {

            itemView.setOnClickListener {
                clickListener(item.filmId)
            }

            repository.getImageUrl(item.filmId) {
                Glide.with(itemView.context)
                    .load(it)
                    .into(imageView)
            }

            movieName.text = item.nameRu ?: item.nameEn
            movieType.text = item.nameEn ?: item.nameRu
            rate.text = item.rating ?: "0.0"

        }


    }
}