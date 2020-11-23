package com.kotlin.marvelgeek

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.util.*


class FavoriteAdapter(private val listaFavoritos: ArrayList<Favorito>, val listener: OnLongClickFavoritoListener): RecyclerView.Adapter<FavoriteAdapter.FavoritosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritosViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
        return FavoritosViewHolder(itemView)
    }

    override fun getItemCount() = listaFavoritos.size

    override fun onBindViewHolder(holder: FavoritosViewHolder, position: Int) {
        var favorito = listaFavoritos.get(position)
        holder.tvNomeHerois.text = favorito.nome
        holder.tvBioHerois.text = favorito.bio
        holder.ivHerois.setImageResource(favorito.img)
    }

    fun notification(name: String){
        notifyDataSetChanged()
    }

    interface OnLongClickFavoritoListener{
        fun onLongClickFavorito(position: Int)
    }

    inner class FavoritosViewHolder(itemView: View):RecyclerView.ViewHolder(itemView), View.OnLongClickListener{
        var tvNomeHerois: TextView = itemView.findViewById(R.id.tvHeroeName)
        var tvBioHerois: TextView = itemView.findViewById(R.id.tvHeroeBio)
        var ivHerois: ImageView = itemView.findViewById(R.id.ivHeroe)

        init{
            itemView.setOnLongClickListener(this)
        }

        override fun onLongClick(v: View?): Boolean {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.onLongClickFavorito(position)
            }
            return true
        }
    }
}