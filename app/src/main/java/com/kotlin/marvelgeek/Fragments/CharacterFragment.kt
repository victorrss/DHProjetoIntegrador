package com.kotlin.marvelgeek.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.marvelgeek.Adapters.ComicAdapter
import com.kotlin.marvelgeek.Adapters.EventAdapter
import com.kotlin.marvelgeek.Adapters.SerieAdapter
import com.kotlin.marvelgeek.Adapters.StorieAdapter
import com.kotlin.marvelgeek.model.Comic
import com.kotlin.marvelgeek.model.Event
import com.kotlin.marvelgeek.model.Serie
import com.kotlin.marvelgeek.model.Storie
import com.kotlin.marvelgeek.R
import kotlinx.android.synthetic.main.activity_character.view.*
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

        view.chaActRvEvents.adapter = adapterEvent
        view.chaActRvSeries.adapter = adapterSerie



        view.chaActRvComics.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL , false)
        view.chaActRvEvents.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL , false)
        view.chaActRvSeries.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL , false)

        view.abHome.setOnClickListener {
            findNavController().navigate(R.id.action_characterFragment_to_favoriteFragment)
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
        findNavController().navigate(R.id.action_characterFragment_to_historiaFragment)
    }



    override fun onClickEvent(position: Int) {
        findNavController().navigate(R.id.action_characterFragment_to_eventFragment)
    }


    override fun onClickSerie(position: Int) {
        findNavController().navigate(R.id.action_characterFragment_to_serieFragment)
    }

    override fun onClickStorie(position: Int) {

    }
}