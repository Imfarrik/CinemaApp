package com.example.skillcinema.ui.adapters.bottom_sheet_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skillcinema.databinding.ItemCollectionBinding
import com.example.skillcinema.model.data.apiSingleMovie.ApiSingleMovie
import com.example.skillcinema.model.data.db.CollectionHolder
import com.example.skillcinema.model.data.db.LikeHolder
import com.example.skillcinema.model.data.db.MoviesInCollection
import com.example.skillcinema.model.data.db.SaveHolder
import com.example.skillcinema.room.AppDatabase
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CollectionAdapter(
    private val appDatabase: AppDatabase,
    private val apiSingleMovie: ApiSingleMovie,
    movies: List<CollectionHolder>,
) : RecyclerView.Adapter<CollectionAdapter.TypeVH>() {

    private val allLists = mutableListOf(
        CollectionHolder("Хочу посмотреть"), CollectionHolder("Любимое")
    )

    init {
        allLists.addAll(movies)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeVH {
        val binding =
            ItemCollectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TypeVH(binding)
    }

    override fun onBindViewHolder(holder: TypeVH, position: Int) {

        val movie = allLists[position]
        holder.initView(movie, position)

    }

    override fun getItemCount(): Int {
        return allLists.size
    }

    inner class TypeVH(private val binding: ItemCollectionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun initView(item: CollectionHolder, pos: Int) = with(binding) {

            when (pos) {
                0 -> {
                    runBlocking {
                        launch {
                            val allMovies = appDatabase.collectionDao().getAllSavedMovie()
                            allMovies.forEach {
                                if (it.movie_id == apiSingleMovie.kinopoiskId) {
                                    checkbox.isChecked = true
                                }
                            }
                            count.text = allMovies.size.toString()
                        }
                    }

                    checkbox.setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked) {
                            runBlocking {
                                launch {
                                    appDatabase.collectionDao().insertSavedMovie(
                                        SaveHolder(
                                            apiSingleMovie.kinopoiskId,
                                            apiSingleMovie.nameRu,
                                            apiSingleMovie.nameEn,
                                            apiSingleMovie.posterUrlPreview,
                                            apiSingleMovie.genres.first().genre,
                                            apiSingleMovie.countries.first().country,
                                            apiSingleMovie.ratingImdb
                                        )
                                    )
                                }
                            }
                        } else {
                            runBlocking {
                                launch {
                                    appDatabase.collectionDao()
                                        .deleteSavedMovie(apiSingleMovie.kinopoiskId)
                                }
                            }
                        }
                    }
                }
                1 -> {
                    runBlocking {
                        launch {
                            val allMovies = appDatabase.collectionDao().getAllLike()
                            allMovies.forEach {
                                if (it.movie_id == apiSingleMovie.kinopoiskId) {
                                    checkbox.isChecked = true
                                }
                            }
                            count.text = allMovies.size.toString()
                        }
                    }

                    checkbox.setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked) {
                            runBlocking {
                                launch {
                                    appDatabase.collectionDao().insertLikedMovie(
                                        LikeHolder(
                                            apiSingleMovie.kinopoiskId,
                                            apiSingleMovie.nameRu,
                                            apiSingleMovie.nameEn,
                                            apiSingleMovie.posterUrlPreview,
                                            apiSingleMovie.genres.first().genre,
                                            apiSingleMovie.countries.first().country,
                                            apiSingleMovie.ratingImdb
                                        )
                                    )
                                }
                            }
                        } else {
                            runBlocking {
                                launch {
                                    appDatabase.collectionDao().deleteLikedMovie(
                                        apiSingleMovie.kinopoiskId
                                    )
                                }
                            }
                        }
                    }
                }
                else -> {
                    runBlocking {
                        launch {
                            val allMovies =
                                appDatabase.collectionDao().getMovieInCollection(item.name)
                            allMovies.forEach {
                                if (it.movie_id == apiSingleMovie.kinopoiskId) {
                                    checkbox.isChecked = true
                                }
                            }
                            count.text = allMovies.size.toString()
                        }
                    }

                    checkbox.setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked) {
                            runBlocking {
                                launch {
                                    appDatabase.collectionDao().insertMovieInCollection(
                                        MoviesInCollection(
                                            apiSingleMovie.kinopoiskId,
                                            apiSingleMovie.nameRu,
                                            apiSingleMovie.nameEn,
                                            apiSingleMovie.posterUrlPreview,
                                            apiSingleMovie.genres.first().genre,
                                            apiSingleMovie.countries.first().country,
                                            apiSingleMovie.ratingImdb,
                                            item.id,
                                            item.name
                                        )
                                    )
                                }
                            }
                        } else {
                            runBlocking {
                                launch {
                                    appDatabase.collectionDao().deleteMovieInCollection(
                                        apiSingleMovie.kinopoiskId, item.name
                                    )
                                }
                            }
                        }
                    }
                }
            }

            name.text = item.name


        }


    }
}