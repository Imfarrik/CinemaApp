package com.example.skillcinema.ui.adapters.all_movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillcinema.databinding.ItemMoviesGridBinding
import com.example.skillcinema.model.data.db.MoviesInCollection
import com.example.skillcinema.model.data.db.SaveHolder

class SavedMoviesAdapter(
    private val movies: List<SaveHolder>,
    private val clickListener: (id: Int) -> Unit,
) :
    RecyclerView.Adapter<SavedMoviesAdapter.TypeVH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeVH {
        val binding =
            ItemMoviesGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

        fun initView(item: SaveHolder) = with(binding) {

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