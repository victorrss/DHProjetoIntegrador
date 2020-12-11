package com.kotlin.marvelgeek.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.marvelgeek.model.Serie
import com.kotlin.marvelgeek.R

class SerieAdapter(
    private val serieList: ArrayList<Serie>,
    val listener: onClickListenerSerie
) : RecyclerView.Adapter<SerieAdapter.ViewHolderSerie>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderSerie {
        return ViewHolderSerie(
            LayoutInflater.from(parent.context).inflate(R.layout.item_series, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolderSerie, position: Int) {
        var serie: Serie = serieList[position]

        holder.serieIvImage.setImageResource(serie.image)
        holder.serieTvTitle.text = serie.title
    }

    override fun getItemCount(): Int = serieList.size

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