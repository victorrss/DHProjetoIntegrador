package com.kotlin.marvelgeek.models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.kotlin.marvelgeek.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_home_personagem.view.ivAvatar
import kotlinx.android.synthetic.main.card_home_personagem.view.tvDescricao
import kotlinx.android.synthetic.main.card_home_personagem.view.tvNome
import kotlinx.android.synthetic.main.item_character.view.*
import java.lang.Exception


class CharacterAdapter(
    val listener: OnClickItemListener) : RecyclerView.Adapter<CharacterAdapter.ItemCharacter>() {

    var listCharacter = ArrayList<Character>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCharacter {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_character, parent, false)
        return ItemCharacter(itemView)
    }

    override fun onBindViewHolder(holder: ItemCharacter, position: Int) {

        val character = listCharacter[position]

        holder.nome.text = character.name
        holder.descricao.text = character.description
        holder.imagem.setImageBitmap(character.thumb)
        holder.background.setCardBackgroundColor(character.color)
    }

    override fun getItemCount() = listCharacter.size

    fun addListCharacter(list: ArrayList<Character>){
            listCharacter.addAll(list)
            notifyDataSetChanged()

    }

    interface OnClickItemListener {
        fun OnClickItem(position: Int)
    }

    inner class ItemCharacter(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val nome: TextView = itemView.tvNome
        val descricao: TextView = itemView.tvDescricao
        val imagem: ImageView = itemView.ivAvatar
        val background : MaterialCardView = itemView.cardBackground

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION)
                listener.OnClickItem(position)
        }
    }



}