package com.kotlin.marvelgeek.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.marvelgeek.R
import kotlinx.android.synthetic.main.card_home_personagem.view.*

class PersonagemAdapter(
    private val personagens: List<Personagem>,
    val listener: OnClickItemListener
) : RecyclerView.Adapter<PersonagemAdapter.ItemPersonagem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemPersonagem {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_home_personagem, parent, false)
        return ItemPersonagem(itemView)
    }

    override fun onBindViewHolder(holder: ItemPersonagem, position: Int) {
        val personagem = personagens[position]
        holder.nome.text = personagem.nome
        holder.descricao.text = personagem.descricao
        holder.imagem.setImageResource(personagem.avatar)
    }

    inner class ItemPersonagem(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val nome: TextView = itemView.tvNome
        val descricao: TextView = itemView.tvDescricao
        val imagem: ImageView = itemView.ivAvatar

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION)
                listener.OnClickItem(position)
        }
    }

    interface OnClickItemListener {
        fun OnClickItem(position: Int)
    }

    override fun getItemCount() = personagens.size
}