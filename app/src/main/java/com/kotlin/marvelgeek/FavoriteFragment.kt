package com.kotlin.marvelgeek

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

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

    @Override
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
        listaFavorito.removeAt(position)
        adapter.notification(name)
    }

    // Retorna uma lista de favoritos fixa
    fun getAllFavorites() = arrayListOf(Favorito(R.drawable.ic_person,"Spider-Man","Bio Spider-Man"),
        Favorito(R.drawable.ic_person,"Captain America","Bio Captain America"),
        Favorito(R.drawable.ic_person,"Captain Marvel","Bio Captain Marvel"),
        Favorito(R.drawable.ic_person,"Hulk","Bio Hulk"),
        Favorito(R.drawable.ic_person,"Thor","Bio Thor"))


}