package com.kotlin.marvelgeek.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.marvelgeek.Entities.SerieC
import com.kotlin.marvelgeek.R
import com.squareup.picasso.Picasso

class SerieAdapter(val listener: onClickListenerSerie) : RecyclerView.Adapter<SerieAdapter.ViewHolderSerie>() {

    var listSerie = ArrayList<SerieC>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderSerie {
        return ViewHolderSerie(
            LayoutInflater.from(parent.context).inflate(R.layout.item_series, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolderSerie, position: Int) {
        var serie = listSerie[position]

        Picasso.get().load("${serie.thumbnail.path}.${serie.thumbnail.extension}")
            .fit()
            .into(holder.serieIvImage)
        holder.serieTvTitle.text = serie.title
    }

    override fun getItemCount(): Int = listSerie.size

    fun addListSerie(list: ArrayList<SerieC>){
        listSerie = list
        notifyDataSetChanged()
    }

    interface onClickListenerSerie {
        fun onClickSerie(position: Int)
    }

    inner class ViewHolderSerie(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var serieIvImage: ImageView = itemView.findViewById(R.id.serieIvImage)
        var serieTvTitle: TextView = itemView.findViewById(R.id.serieTvTitle)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition

            if (position != RecyclerView.NO_POSITION) {
                listener.onClickSerie(position)
            }
        }
    }
}