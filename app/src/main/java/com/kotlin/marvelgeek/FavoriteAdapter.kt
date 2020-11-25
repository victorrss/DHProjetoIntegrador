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
import com.kotlin.marvelgeek.model.Personagem
import kotlinx.android.synthetic.main.card_home_personagem.view.*
import java.util.*


class FavoriteAdapter(private val listaFavoritos: ArrayList<Personagem>, val listener: ListenerOnClickFavorito): RecyclerView.Adapter<FavoriteAdapter.FavoritosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritosViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_home_personagem, parent, false)
        return FavoritosViewHolder(itemView)
    }

    override fun getItemCount() = listaFavoritos.size

    override fun onBindViewHolder(holder: FavoritosViewHolder, position: Int) {
        var favorito = listaFavoritos.get(position)
        holder.nome.text = favorito.nome
        holder.descricao.text = favorito.descricao
        holder.imagem.setImageResource(favorito.avatar)
    }

    fun notification(name: String){
        notifyDataSetChanged()
    }

    interface ListenerOnClickFavorito{
        fun onLongClickFavorito(position: Int)
        fun onClickFavorito(position: Int)
    }

    inner class FavoritosViewHolder(itemView: View):RecyclerView.ViewHolder(itemView), View.OnLongClickListener, View.OnClickListener{
        val nome: TextView = itemView.tvNome
        val descricao: TextView = itemView.tvDescricao
        val imagem: ImageView = itemView.ivAvatar

        init{
            itemView.setOnLongClickListener(this)
            itemView.setOnClickListener(this)
        }

        override fun onLongClick(v: View?): Boolean {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.onLongClickFavorito(position)
            }
            return true
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.onClickFavorito(position)
            }
        }
    }
}