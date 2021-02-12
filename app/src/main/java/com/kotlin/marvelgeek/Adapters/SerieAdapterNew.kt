package com.kotlin.marvelgeek.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.marvelgeek.Entities.SerieC
import com.kotlin.marvelgeek.R
import com.squareup.picasso.Picasso

class SerieAdapterNew() : RecyclerView.Adapter<SerieAdapterNew.ViewHolderSerieNew>() {

    var listSerie = ArrayList<SerieC>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderSerieNew {
        return ViewHolderSerieNew(
            LayoutInflater.from(parent.context).inflate(R.layout.item_series, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolderSerieNew, position: Int) {
        var serie = listSerie[position]

        Picasso.get().load("${serie.thumbnail.path}.${serie.thumbnail.extension}")
            .fit()
            .into(holder.serieIvImage)
        holder.serieTvTitle.text = serie.title
    }

    override fun getItemCount(): Int = listSerie.size

    inner class ViewHolderSerieNew(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        var serieIvImage: ImageView = itemView.findViewById(R.id.serieIvImage)
        var serieTvTitle: TextView = itemView.findViewById(R.id.serieTvTitle)
    }

    fun addListSerie(list: ArrayList<SerieC>) {
        listSerie = list
        notifyDataSetChanged()
    }
}