package com.kotlin.marvelgeek.Fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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
import com.kotlin.marvelgeek.models.Character
import com.kotlin.marvelgeek.services.repository
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_character.view.*
import kotlinx.android.synthetic.main.activity_home.view.abHome

class CharacterFragment : Fragment(), ComicAdapter.onClickListenerComic{
    //StorieAdapter.onClickListenerStorie,
    //EventAdapter.onClickListenerEvent,
    //SerieAdapter.onClickListenerSerie {

    val viewModel by viewModels<MainViewModel>{
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(repository) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_character, container, false)
        var mBundle =  Bundle()
        if(mBundle != null) {
            mBundle = requireArguments()
        }
        var character =  mBundle.getSerializable("character") as Character
        (activity as AppCompatActivity).supportActionBar?.setTitle(character.name)


        Picasso.get().load("${character.thumbnail.path}.${character.thumbnail.extension}")
            .fit()
            .into(view.chaActIvHero)

        view.chaActTvBio.text = character.description

        var adapterComic = ComicAdapter(this)
        view.chaActRvComics.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL , false)

        // Comic
        character.comics.items.forEach {
            var id = it.resourceURI.split("/")
            //Atualizando os valores da lista
            var error = viewModel.getComic(id[id.size-1].toLong())
            if (error != null){
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Server problem:")
                builder.setIcon(R.drawable.ic_info)
                builder.setMessage(error)
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        }

        while(viewModel.viewModelScope.isActive);
        viewModel.listComic.observe(viewLifecycleOwner){
            adapterComic.addListComic(it)
        }



        //var adapterStorie = StorieAdapter(storieList, this)
        //var adapterEvent = EventAdapter(eventList, this)
        //var adapterSerie = SerieAdapter(serieList, this)

        //view.chaActRvComics.adapter = adapterComic
        //view.chaActRvEvents.adapter = adapterEvent
        //view.chaActRvSeries.adapter = adapterSerie


        //view.chaActRvEvents.layoutManager = LinearLayoutManager(activity,
        //LinearLayoutManager.HORIZONTAL , false)
        //view.chaActRvSeries.layoutManager = LinearLayoutManager(activity,
        //LinearLayoutManager.HORIZONTAL , false)

        view.abHome.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_characterFragment_to_favoriteFragment)
        }

        view.fbQuiz.setOnClickListener {
            findNavController().navigate(R.id.action_characterFragment_to_quizFragment)
        }
        return view
    }

    override fun onClickComic(position: Int) {
        //findNavController().navigate(R.id.action_characterFragment_to_historiaFragment)
    }



//    override fun onClickEvent(position: Int) {
//        //findNavController().navigate(R.id.action_characterFragment_to_eventFragment)
//    }
//
//
//    override fun onClickSerie(position: Int) {
//        //findNavController().navigate(R.id.action_characterFragment_to_serieFragment)
//    }
//
//    override fun onClickStorie(position: Int) {
//        //findNavController().navigate(R.id.action_characterFragment_to_serieFragment)
//    }
}