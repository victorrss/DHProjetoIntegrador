package com.kotlin.marvelgeek.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.marvelgeek.model.Comic
import com.kotlin.marvelgeek.R

class ComicAdapter(
    private val comicList: ArrayList<Comic>,
    val listener: onClickListenerComic
) : RecyclerView.Adapter<ComicAdapter.ViewHolderComic>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderComic {
        return ViewHolderComic(
            LayoutInflater.from(parent.context).inflate(R.layout.item_comics, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolderComic, position: Int) {
        var comic: Comic = comicList[position]

        holder.comicIvImage.setImageResource(comic.image)
        holder.comicTvTitle.text = comic.title
    }

    override fun getItemCount(): Int = comicList.size

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