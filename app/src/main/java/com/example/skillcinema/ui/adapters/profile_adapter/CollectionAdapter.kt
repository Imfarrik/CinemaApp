package com.example.skillcinema.ui.adapters.profile_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillcinema.R
import com.example.skillcinema.databinding.ItemCollectionsBinding
import com.example.skillcinema.model.data.db.CollectionHolder
import com.example.skillcinema.room.AppDatabase
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CollectionAdapter(
    private val appDatabase: AppDatabase,
    collection: List<CollectionHolder>,
    private val clickListener: (collection_name: String) -> Unit,
) :
    RecyclerView.Adapter<CollectionAdapter.TypeVH>() {

    private val allLists = mutableListOf(
        CollectionHolder("Хочу посмотреть"), CollectionHolder("Любимое")
    )

    init {
        allLists.addAll(collection)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeVH {
        val binding =
            ItemCollectionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TypeVH(binding)
    }

    override fun onBindViewHolder(holder: TypeVH, position: Int) {

        val item = allLists[position]
        holder.initView(item, position)

    }

    override fun getItemCount(): Int {
        return allLists.size
    }

    inner class TypeVH(private val binding: ItemCollectionsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun initView(item: CollectionHolder, pos: Int) = with(binding) {

            itemView.setOnClickListener {
                clickListener(item.name)
            }

            collectionName.text = item.name

            when (pos) {
                0 -> {
                    imageView.setImageResource(R.drawable.save_black)
                    btnX.isVisible = false
                    runBlocking {
                        launch {
                            val list = appDatabase.collectionDao().getAllSavedMovie()
                            count.text = list.size.toString()
                        }
                    }
                }
                1 -> {
                    btnX.isVisible = false
                    imageView.setImageResource(R.drawable.like_black)
                    runBlocking {
                        launch {
                            val list = appDatabase.collectionDao().getAllLike()
                            count.text = list.size.toString()
                        }
                    }
                }
                else -> {
                    imageView.setImageResource(R.drawable.other_black)
                    runBlocking {
                        launch {
                            val list = appDatabase.collectionDao().getMovieInCollection(item.name)
                            count.text = list.size.toString()
                        }
                    }

                    btnX.setOnClickListener {
                        runBlocking {
                            launch {
                                appDatabase.collectionDao()
                                    .deleteCollection(CollectionHolder(item.name, item.id))
                            }
                        }
                    }
                }
            }

        }

    }
}