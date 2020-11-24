package com.kotlin.marvelgeek

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : Fragment(), FavoriteAdapter.OnLongClickFavoritoListener {

    var listaFavorito = getAllFavorites()
    var adapter = FavoriteAdapter(listaFavorito,this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rvFavorite.adapter = adapter
        rvFavorite.layoutManager = LinearLayoutManager(context)
        rvFavorite.setHasFixedSize(false)
    }

    companion object{
        fun newInstance() = FavoriteFragment()
    }

    override fun onLongClickFavorito(position: Int) {

        val name = listaFavorito[position].nome

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Excluir")
        builder.setIcon(R.drawable.ic_delete)
        builder.setMessage("Você deseja excluir $name da sua lista de favoritos?")
        builder.setPositiveButton("Sim"){dialog, which ->
            listaFavorito.removeAt(position)
            adapter.notification(name)
        }
        builder.setNegativeButton("Não"){dialog,which ->
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    // Retorna uma lista de favoritos fixa
    fun getAllFavorites() = arrayListOf(Favorito(R.drawable.ic_person,"Spider-Man","Bio Spider-Man"),
        Favorito(R.drawable.ic_person,"Captain America","Bio Captain America"),
        Favorito(R.drawable.ic_person,"Captain Marvel","Bio Captain Marvel"),
        Favorito(R.drawable.ic_person,"Hulk","Bio Hulk"),
        Favorito(R.drawable.ic_person,"Thor","Bio Thor"))


}