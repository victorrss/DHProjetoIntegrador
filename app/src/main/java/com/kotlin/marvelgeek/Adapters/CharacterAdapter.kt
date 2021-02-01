package com.kotlin.marvelgeek.models

import android.graphics.Color
import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.kotlin.marvelgeek.R
import kotlinx.android.synthetic.main.card_home_personagem.view.ivAvatar
import kotlinx.android.synthetic.main.card_home_personagem.view.tvDescricao
import kotlinx.android.synthetic.main.card_home_personagem.view.tvNome
import kotlinx.android.synthetic.main.item_character.view.*
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation


class CharacterAdapter(val listener: OnClickItemListener) : RecyclerView.Adapter<CharacterAdapter.ItemCharacter>() {

    var listCharacter = ArrayList<Character>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCharacter {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_character, parent, false)
        return ItemCharacter(itemView)
    }

    override fun onBindViewHolder(holder: ItemCharacter, position: Int) {
        val character = listCharacter[position]
        var hsv: FloatArray = floatArrayOf((0).toFloat(),(0).toFloat(),(0).toFloat())

        Picasso.get().load(character.thumbnail.path + "." + character.thumbnail.extension)
            .fit()
            .centerCrop()
            .noFade()
            .transform(CropCircleTransformation())
            .placeholder(R.drawable.progress_bar)
            .into(holder.imagem)

        if(character.color != null){
            holder.background.setCardBackgroundColor(Color.parseColor(character.color!!))
            Color.RGBToHSV(Color.parseColor(character.color).red,
                Color.parseColor(character.color).green,
                Color.parseColor(character.color).blue, hsv)
            character.brightness = hsv[2]
        }


        if(character.brightness < 0.5){
            holder.nome.setTextColor(ContextCompat.getColor(holder.nome.context, R.color.lightGray))
            holder.descricao.setTextColor(ContextCompat.getColor(holder.nome.context, R.color.lightGray))
        }else{
            holder.nome.setTextColor(ContextCompat.getColor(holder.nome.context, R.color.darkgray))
            holder.descricao.setTextColor(ContextCompat.getColor(holder.nome.context, R.color.darkgray))
        }

        if (character.description.isNullOrEmpty())
            holder.descricao.text = "Sorry, there is no description to this Character."
        else
            holder.descricao.text = character.description
        holder.nome.text = character.name
    }

    override fun getItemCount() = listCharacter.size

    fun addListCharacter(list: ArrayList<Character>){
            listCharacter.addAll(list)
            notifyAdapter()
    }

    fun notifyAdapter(){
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