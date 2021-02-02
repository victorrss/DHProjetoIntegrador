package com.kotlin.marvelgeek.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.marvelgeek.R
import com.kotlin.marvelgeek.models.Character
import com.squareup.picasso.Picasso

class CharacterAdapterNew() : RecyclerView.Adapter<CharacterAdapterNew.ViewHolderCharacterNew>() {

    var listCharacter = ArrayList<Character>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacterAdapterNew.ViewHolderCharacterNew {
        return ViewHolderCharacterNew(
            LayoutInflater.from(parent.context).inflate(R.layout.item_comics, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CharacterAdapterNew.ViewHolderCharacterNew, position: Int) {
        var character = listCharacter[position]

        Picasso.get().load("${character.thumbnail.path}.${character.thumbnail.extension}")
            .fit()
            .into(holder.comicIvImage)
        holder.comicTvTitle.text = character.name
    }

    override fun getItemCount(): Int = listCharacter.size

    inner class ViewHolderCharacterNew(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        var comicIvImage: ImageView = itemView.findViewById(R.id.comicIvImage)
        var comicTvTitle: TextView = itemView.findViewById(R.id.comicTvTitle)

    }

    fun addListComic(list: ArrayList<Character>) {
        listCharacter = list
        notifyDataSetChanged()
    }
}