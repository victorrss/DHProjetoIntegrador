package com.kotlin.marvelgeek.Adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.kotlin.marvelgeek.R
import com.kotlin.marvelgeek.R.layout.item_character
import com.kotlin.marvelgeek.model.Personagem
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.card_home_personagem.view.tvDescricao
import kotlinx.android.synthetic.main.card_home_personagem.view.tvNome
import kotlinx.android.synthetic.main.item_character.view.*
import java.util.*


class FavoriteAdapter(val listener: ListenerOnClickFavorito) :
    RecyclerView.Adapter<FavoriteAdapter.FavoritosViewHolder>() {

    var listFavorite = ArrayList<Personagem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritosViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(item_character, parent, false)
        return FavoritosViewHolder(itemView)
    }

    override fun getItemCount() = listFavorite.size

    override fun onBindViewHolder(holder: FavoritosViewHolder, position: Int) {
        val character = listFavorite[position]

        Picasso.get().load(character.thumbnail)
            .fit()
            .centerCrop()
            .noFade()
            .transform(CropCircleTransformation())
            .placeholder(R.drawable.progress_bar)
            .into(holder.imagem)

        if (character.color != null && character.color != "null") {
            holder.background.setCardBackgroundColor(Color.parseColor(character.color!!))
        }

        if (character.brightness < 0.5) {
            holder.nome.setTextColor(ContextCompat.getColor(holder.nome.context, R.color.lightGray))
            holder.descricao.setTextColor(
                ContextCompat.getColor(
                    holder.nome.context,
                    R.color.lightGray
                )
            )
        } else {
            holder.nome.setTextColor(ContextCompat.getColor(holder.nome.context, R.color.darkgray))
            holder.descricao.setTextColor(
                ContextCompat.getColor(
                    holder.nome.context,
                    R.color.darkgray
                )
            )
        }

        if (character.description.isNullOrEmpty())
            holder.descricao.text = "Sorry, there is no description to this Character."
        else
            holder.descricao.text = character.description
        holder.nome.text = character.name
    }

    fun addListFavorite(list: ArrayList<Personagem>) {
        listFavorite = list
        notifyAdapter()
    }

    fun notifyAdapter() {
        notifyDataSetChanged()
    }

    interface ListenerOnClickFavorito {
        fun onLongClickFavorito(position: Int)
        fun onClickFavorito(position: Int)
    }

    inner class FavoritosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnLongClickListener, View.OnClickListener {
        val nome: TextView = itemView.tvNome
        val descricao: TextView = itemView.tvDescricao
        val imagem: ImageView = itemView.ivCharacter
        val background: MaterialCardView = itemView.cardBackground

        init {
            itemView.setOnLongClickListener(this)
            itemView.setOnClickListener(this)
        }

        override fun onLongClick(v: View?): Boolean {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onLongClickFavorito(position)
            }
            return true
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onClickFavorito(position)
            }
        }
    }
}