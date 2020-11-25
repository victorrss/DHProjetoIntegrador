package com.kotlin.marvelgeek.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.marvelgeek.R

class StorieAdapter(
    private val storieList: ArrayList<Storie>,
    val listener: onClickListenerStorie
) : RecyclerView.Adapter<StorieAdapter.ViewHolderStorie>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderStorie {
        return ViewHolderStorie(
            LayoutInflater.from(parent.context).inflate(R.layout.item_stories, parent, false)
        )
    }

    override fun onBindViewHolder(holder: StorieAdapter.ViewHolderStorie, position: Int) {
        var storie: Storie = storieList[position]

        holder.storieIvImage.setImageResource(storie.image)
        holder.storieTvTitle.text = storie.title
    }

    override fun getItemCount(): Int = storieList.size

    interface onClickListenerStorie {
        fun onClickStorie(position: Int)
    }

    inner class ViewHolderStorie(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var storieIvImage: ImageView = itemView.findViewById(R.id.storieIvImage)
        var storieTvTitle: TextView = itemView.findViewById(R.id.storieTvTitle)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition

            if (position != RecyclerView.NO_POSITION) {
                listener.onClickStorie(position)
            }
        }
    }
}