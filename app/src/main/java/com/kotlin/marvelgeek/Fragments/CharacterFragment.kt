package com.kotlin.marvelgeek.Fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.lifecycle.viewModelScope
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
import com.kotlin.marvelgeek.ViewModel.MainViewModel
import com.kotlin.marvelgeek.models.Character
import com.kotlin.marvelgeek.services.repository
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_character.view.*
import kotlinx.android.synthetic.main.activity_character.view.fbQuiz
import kotlinx.android.synthetic.main.activity_home.view.*
import kotlinx.android.synthetic.main.activity_home.view.abHome

class CharacterFragment : Fragment(), ComicAdapter.onClickListenerComic,
    EventAdapter.onClickListenerEvent,
    SerieAdapter.onClickListenerSerie {
    //StorieAdapter.onClickListenerStorie,

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_character, container, false)

        var character =  viewModel.charecter.value
        (activity as AppCompatActivity).supportActionBar?.setTitle(character!!.name)


        Picasso.get().load("${character!!.thumbnail.path}.${character!!.thumbnail.extension}")
            .fit()
            .into(view.chaActIvHero)

        view.chaActTvBio.text = character.description

        // Comic--------------------------------------------------------------------------------------------------------------
        var adapterComic = ComicAdapter(this)
        view.chaActRvComics.adapter = adapterComic
        view.chaActRvComics.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL , false)
        view.chaActRvComics.setHasFixedSize(true)
        var errorComic = viewModel.getComic(character.id)
        if (errorComic != null){
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Server problem:")
            builder.setIcon(R.drawable.ic_info)
            builder.setMessage(errorComic)
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
        viewModel.listComic.observe(viewLifecycleOwner){
            adapterComic.addListComic(it)
        }

        // Event--------------------------------------------------------------------------------------------------------------
        var adapterEvent = EventAdapter(this)
        view.chaActRvEvents.adapter = adapterEvent
        view.chaActRvEvents.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL , false)
        view.chaActRvEvents.setHasFixedSize(true)
        var errorEvent = viewModel.getEvent(character.id)
        if (errorEvent != null){
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Server problem:")
            builder.setIcon(R.drawable.ic_info)
            builder.setMessage(errorEvent)
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
        viewModel.listEvent.observe(viewLifecycleOwner){
            adapterEvent.addListEvent(it)
        }

        // Serie--------------------------------------------------------------------------------------------------------------
        var adapterSerie = SerieAdapter(this)
        view.chaActRvSeries.adapter = adapterSerie
        view.chaActRvSeries.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL , false)
        view.chaActRvSeries.setHasFixedSize(true)
        var errorSeries = viewModel.getSerie(character.id)
        if (errorSeries != null){
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Server problem:")
            builder.setIcon(R.drawable.ic_info)
            builder.setMessage(errorEvent)
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
        viewModel.listSerie.observe(viewLifecycleOwner){
            adapterSerie.addListSerie(it)
        }

        view.abHome.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_characterFragment_to_favoriteFragment)
        }

        view.fbQuiz.setOnClickListener {
            findNavController().navigate(R.id.action_characterFragment_to_quizFragment)
        }
        return view
    }

    override fun onClickComic(position: Int) {
        val comic = viewModel.listComic.value!!.get(position)
       viewModel.setComic(comic)
        findNavController().navigate(R.id.action_characterFragment_to_historiaFragment)
    }

    override fun onClickEvent(position: Int) {
        val event = viewModel.listEvent.value!!.get(position)
        viewModel.setEvent(event)
        findNavController().navigate(R.id.action_characterFragment_to_eventFragment)
    }


    override fun onClickSerie(position: Int) {
        val serie = viewModel.listSerie.value!!.get(position)
        viewModel.setSerie(serie)
        findNavController().navigate(R.id.action_characterFragment_to_serieFragment)
    }

//    override fun onClickStorie(position: Int) {
//        //findNavController().navigate(R.id.action_characterFragment_to_serieFragment)
//    }
}