package com.kotlin.marvelgeek.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.marvelgeek.R
import com.kotlin.marvelgeek.models.ComicC
import com.squareup.picasso.Picasso

class ComicAdapter(val listener: onClickListenerComic) : RecyclerView.Adapter<ComicAdapter.ViewHolderComic>() {

    var listComic = ArrayList<ComicC>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ComicAdapter.ViewHolderComic {
        return ViewHolderComic(
            LayoutInflater.from(parent.context).inflate(R.layout.item_comics, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ComicAdapter.ViewHolderComic, position: Int) {
        var comic: ComicC = listComic[position]

        Picasso.get().load("${comic.thumbnail.path}.${comic.thumbnail.extension}")
            .fit()
            .into(holder.comicIvImage)
        holder.comicTvTitle.text = comic.title
    }

    override fun getItemCount(): Int = listComic.size

    fun addListComic(list: ArrayList<ComicC>){
        listComic = list
        Log.i("Adapter",listComic.toString())
        notifyDataSetChanged()
    }

    interface onClickListenerComic {
        fun onClickComic(position: Int)
    }

    inner class ViewHolderComic(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var comicIvImage: ImageView = itemView.findViewById(R.id.comicIvImage)
        var comicTvTitle: TextView = itemView.findViewById(R.id.comicTvTitle)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition

            if (position != RecyclerView.NO_POSITION) {
                listener.onClickComic(position)
            }
        }
    }
}