package com.example.skillcinema.ui.adapters.filmography_page_adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillcinema.R
import com.example.skillcinema.databinding.ItemGalleryBinding
import com.example.skillcinema.databinding.ItemTypeBinding
import com.example.skillcinema.domain.Constants
import com.example.skillcinema.model.data.apiImages.Item
import com.example.skillcinema.model.data.apiSingleStaff.ApiSingleStaff
import com.example.skillcinema.model.data.apiSingleStaff.Film

class TypeAdapter(
    private val getMovies: ApiSingleStaff,
    private val clickListener: (list: List<Film>) -> Unit,
) :
    RecyclerView.Adapter<TypeAdapter.TypeVH>() {

    private var singleItemSelectionPosition = 0

    private val writer = mutableListOf<Film>()
    private val director = mutableListOf<Film>()
    private val producer = mutableListOf<Film>()
    private val himself = mutableListOf<Film>()
    private val actor = mutableListOf<Film>()
    private val others = mutableListOf<Film>()

    private val allLists = listOf(writer, director, producer, himself, actor, others)

    init {
        getMovies.films.forEach {
            when (it.professionKey) {
                Constants.WRITER -> writer.add(it)
                Constants.DIRECTOR -> director.add(it)
                Constants.PRODUCER -> producer.add(it)
                Constants.HIMSELF -> himself.add(it)
                Constants.ACTOR -> actor.add(it)
                else -> others.add(it)
            }
        }
    }

    private val data =
        mutableListOf(
            "Писатель",
            "Режиссер",
            "Продюссер",
            "Играет самого себя",
            "Актер",
            "Другие"
        )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeVH {
        val binding = ItemTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TypeVH(binding)
    }

    override fun onBindViewHolder(holder: TypeVH, position: Int) {

        val image = data[position]
        holder.initView(image, position)

    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class TypeVH(private val binding: ItemTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun initView(item: String, pos: Int) = with(binding) {

            if (getMovies.sex != "MALE") {
                data[4] = "Играет саму себя"
                data[5] = "Актриса"
            }

            when (pos) {
                0 -> typeCount.text = writer.size.toString()
                1 -> typeCount.text = director.size.toString()
                2 -> typeCount.text = producer.size.toString()
                3 -> typeCount.text = himself.size.toString()
                4 -> typeCount.text = actor.size.toString()
                5 -> typeCount.text = others.size.toString()
            }

            itemView.setOnClickListener {
                setSingleSelection()
            }

            if (singleItemSelectionPosition == pos) {
                cardView.setCardBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.main
                    )
                )
                cardView.strokeWidth = 0
                typeName.setTextColor(Color.WHITE)
                clickListener(allLists[pos])
            } else {
                cardView.strokeWidth = 1
                cardView.setCardBackgroundColor(Color.WHITE)
                typeName.setTextColor(Color.BLACK)
            }

            typeName.text = item


        }

        private fun setSingleSelection() {
            if (absoluteAdapterPosition == RecyclerView.NO_POSITION) return
            notifyItemChanged(singleItemSelectionPosition)
            singleItemSelectionPosition = absoluteAdapterPosition
            notifyItemChanged(singleItemSelectionPosition)
        }


    }
}