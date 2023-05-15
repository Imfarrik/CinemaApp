package com.example.skillcinema.ui.adapters.seasons_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.skillcinema.databinding.ItemSeasonsDetailsBinding
import com.example.skillcinema.model.data.apiSeason.ApiSeason
import com.example.skillcinema.model.data.apiSeason.Episode
import com.example.skillcinema.model.data.apiSeason.Item
import java.text.SimpleDateFormat
import java.util.*

class SeasonsAdapter(
    private val data: List<Episode>,
) :
    RecyclerView.Adapter<SeasonsAdapter.TypeVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeVH {
        val binding =
            ItemSeasonsDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TypeVH(binding)
    }

    override fun onBindViewHolder(holder: TypeVH, position: Int) {

        val item = data[position]
        holder.initView(item)

    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class TypeVH(private val binding: ItemSeasonsDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun initView(item: Episode) = with(binding) {

            name.text = "${item.episodeNumber} серия. ${item.nameRu ?: item.nameEn}"

            if (item.releaseDate != null) {
                date.text = convertDateFormat(item.releaseDate)
            } else {
                date.isVisible = false
            }

        }

    }

    private fun convertDateFormat(dateString: String): String? {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

        val date = inputFormat.parse(dateString)
        return date?.let { outputFormat.format(it) }
    }
}