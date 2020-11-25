package com.kotlin.marvelgeek.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.marvelgeek.EventActivity
import com.kotlin.marvelgeek.R
import com.kotlin.marvelgeek.model.*
import kotlinx.android.synthetic.main.activity_character.*
import kotlinx.android.synthetic.main.activity_character.view.*
import kotlinx.android.synthetic.main.activity_home.view.*
import kotlinx.android.synthetic.main.activity_home.view.abHome

class CharacterFragment : Fragment(), ComicAdapter.onClickListenerComic, StorieAdapter.onClickListenerStorie, EventAdapter.onClickListenerEvent, SerieAdapter.onClickListenerSerie {
    var comicList = getAllComics()
    var storieList = getAllStories()
    var eventList = getAllEvents()
    var serieList = getAllSeries()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_character, container, false)
        var adapterComic = ComicAdapter(comicList, this)
        var adapterStorie = StorieAdapter(storieList, this)
        var adapterEvent = EventAdapter(eventList, this)
        var adapterSerie = SerieAdapter(serieList, this)

        view.chaActRvComics.adapter = adapterComic
        view.chaActRvStories.adapter = adapterStorie
        view.chaActRvEvents.adapter = adapterEvent
        view.chaActRvSeries.adapter = adapterSerie

        view.chaActRvComics.layoutManager = LinearLayoutManager(activity)
        view.chaActRvStories.layoutManager = LinearLayoutManager(activity)
        view.chaActRvEvents.layoutManager = LinearLayoutManager(activity)
        view.chaActRvSeries.layoutManager = LinearLayoutManager(activity)

        view.abHome.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment2_to_favoriteFragment)
        }

        return view
    }

    fun getAllComics() = arrayListOf(
        Comic("Immortal Hulk", "a", R.drawable.immortal_hulk),
        Comic("Immortal Hulk #37", "b", R.drawable.imortal_hulk_37),
        Comic("Immortal Hulk #38", "c", R.drawable.imortal_hulk_38),
        Comic("Immortal Hulk #39", "d", R.drawable.immortal_hulk_39),
        Comic("Juggernaut", "e", R.drawable.juggernaut)
    )

    fun getAllStories() = arrayListOf(
        Storie("Immortal Hulk #37", "b", R.drawable.imortal_hulk_37),
        Storie("Immortal Hulk #38", "c", R.drawable.imortal_hulk_38),
        Storie("Immortal Hulk #39", "d", R.drawable.immortal_hulk_39),
        Storie("Immortal Hulk", "a", R.drawable.immortal_hulk),
        Storie("Juggernaut", "e", R.drawable.juggernaut)
    )

    fun getAllEvents() = arrayListOf(
        Event("Juggernaut", "e", R.drawable.juggernaut),
        Event("Immortal Hulk", "a", R.drawable.immortal_hulk),
        Event("Immortal Hulk #37", "b", R.drawable.imortal_hulk_37),
        Event("Immortal Hulk #38", "c", R.drawable.imortal_hulk_38),
        Event("Immortal Hulk #39", "d", R.drawable.immortal_hulk_39)
    )

    fun getAllSeries() = arrayListOf(
        Serie("Immortal Hulk", "a", R.drawable.immortal_hulk),
        Serie("Immortal Hulk #37", "b", R.drawable.imortal_hulk_37),
        Serie("Immortal Hulk #38", "c", R.drawable.imortal_hulk_38),
        Serie("Immortal Hulk #39", "d", R.drawable.immortal_hulk_39),
        Serie("Juggernaut", "e", R.drawable.juggernaut)
    )

    override fun onClickComic(position: Int) {
    }

    override fun onClickStorie(position: Int) {
    }

    override fun onClickEvent(position: Int) {
    }

    override fun onClickSerie(position: Int) {
    }
}