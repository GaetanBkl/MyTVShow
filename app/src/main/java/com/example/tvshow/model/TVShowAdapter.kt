package com.example.tvshow.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.tvshow.R
import java.util.Collections.addAll

class TVShowAdapter(
    private var tvshow: MutableList<TVShow>
) : RecyclerView.Adapter<TVShowAdapter.TVShowViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_tvshow, parent, false)
        return TVShowViewHolder(view)
    }

    override fun getItemCount(): Int = tvshow.size

    override fun onBindViewHolder(holder: TVShowViewHolder, position: Int) {
        holder.bind(tvshow[position])
    }

    fun appendTVShow(tvshow: List<TVShow>) {
        this.tvshow.addAll(tvshow)
        notifyItemRangeInserted(
            this.tvshow.size,
            tvshow.size - 1
        )
    }

    inner class TVShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val poster: ImageView = itemView.findViewById(R.id.item_tvshow_poster)

        fun bind(tvshow: TVShow) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w342${tvshow.posterPath}")
                .transform(CenterCrop())
                .into(poster)
        }
    }
}