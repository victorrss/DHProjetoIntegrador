package com.kotlin.marvelgeek.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.marvelgeek.R
import com.kotlin.marvelgeek.models.ComicC
import com.squareup.picasso.Picasso

class ComicAdapterNew : RecyclerView.Adapter<ComicAdapterNew.ViewHolderComicNew>() {

    var listComic = ArrayList<ComicC>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ComicAdapterNew.ViewHolderComicNew {
        return ViewHolderComicNew(
            LayoutInflater.from(parent.context).inflate(R.layout.item_comics, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ComicAdapterNew.ViewHolderComicNew, position: Int) {
        var comic: ComicC = listComic[position]

        Picasso.get().load("${comic.thumbnail.path}.${comic.thumbnail.extension}")
            .fit()
            .into(holder.comicIvImage)
        holder.comicTvTitle.text = comic.title
    }

    override fun getItemCount(): Int = listComic.size

    fun addListComic(list: ArrayList<ComicC>){
        listComic = list
        notifyDataSetChanged()
    }

    inner class ViewHolderComicNew(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        var comicIvImage: ImageView = itemView.findViewById(R.id.comicIvImage)
        var comicTvTitle: TextView = itemView.findViewById(R.id.comicTvTitle)
    }
}