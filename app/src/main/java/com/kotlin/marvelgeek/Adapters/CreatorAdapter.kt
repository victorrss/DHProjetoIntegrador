package com.kotlin.marvelgeek.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.marvelgeek.Entities.CreatorID
import com.kotlin.marvelgeek.R
import com.kotlin.marvelgeek.models.Character
import com.squareup.picasso.Picasso

class CreatorAdapter() : RecyclerView.Adapter<CreatorAdapter.ViewHolderCreator>() {

    var listCreator = ArrayList<CreatorID>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CreatorAdapter.ViewHolderCreator {
        return ViewHolderCreator(
            LayoutInflater.from(parent.context).inflate(R.layout.item_autor, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CreatorAdapter.ViewHolderCreator, position: Int) {
        var creator: CreatorID = listCreator[position]

        holder.comicTvTitle.text = creator.fullName
    }

    override fun getItemCount(): Int = listCreator.size

    inner class ViewHolderCreator(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        var comicTvTitle: TextView = itemView.findViewById(R.id.autorName)
    }

    fun addListCreator(list: ArrayList<CreatorID>) {
        listCreator = list
        notifyDataSetChanged()
    }
}