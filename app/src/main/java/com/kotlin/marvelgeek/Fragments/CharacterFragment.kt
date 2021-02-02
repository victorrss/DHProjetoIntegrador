package com.kotlin.marvelgeek.Fragments

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.marvelgeek.Adapters.*
import com.kotlin.marvelgeek.R
import com.kotlin.marvelgeek.ViewModel.MainViewModel
import com.kotlin.marvelgeek.models.Character
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_character.view.*
import kotlinx.android.synthetic.main.activity_character.view.fbQuiz

class CharacterFragment : Fragment(), ComicAdapter.onClickListenerComic,
    EventAdapter.onClickListenerEvent,
    SerieAdapter.onClickListenerSerie{

    lateinit var character: Character
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_character, container, false)

        var mBundle =  Bundle()
        if(mBundle != null) {
            mBundle = requireArguments()
            character =  mBundle.getSerializable("character") as Character
        }

        if(character.color != null){
            setColor(view, character.color!!,character.brightness)
        }

        (activity as AppCompatActivity).supportActionBar?.title = character.name
        Picasso.get().load("${character.thumbnail.path}.${character.thumbnail.extension}")
            .fit()
            .into(view.chaActIvHero)

        if (character.description.isNullOrEmpty())
            view.chaActTvBio.text = "Sorry, there is no description to this Character."
        else
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
        var errorSerie = viewModel.getSerie(character.id)
        if (errorSerie != null){
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

        view.icFavorite.setOnClickListener {
            if(viewModel.user != null) {
                if (view.icFavorite.tag == R.drawable.ic_favorite) {
                    viewModel.addFavorite(character)
                    view.icFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                } else {
                    viewModel.removeFavoriteCharacter(character)
                }
            }else{
                viewModel.showToast(view.context,"Para favoritar, entre com uma conta.")
            }
        }

        view.goChaHome.setOnClickListener {
            findNavController().navigate(R.id.action_characterFragment_to_action_homeFragment2)
        }

        view.goChaFavo.setOnClickListener {
            findNavController().navigate(R.id.action_characterFragment_to_favoriteFragment)
        }

        view.fbQuiz.setOnClickListener {
            findNavController().navigate(R.id.action_characterFragment_to_quizFragment)
        }
        return view
    }

    private fun setColor(view: View, color: String,brigthness: Float){

        var gray = 0

        view.backgroundCharacter.setBackgroundColor(Color.parseColor(color))
        gray = if(brigthness < 0.5){
            R.color.lightGray
        }else{
            R.color.darkgray
        }
        view.chaActTvLabelBio.setTextColor(Color.parseColor(color))
        view.chaActTvBio.setTextColor(gray)
        view.chaActTvLabelComics.setTextColor(gray)
        view.chaActTvLabelEvents.setTextColor(gray)
        view.chaActTvLabelSeries.setTextColor(gray)
    }

    override fun onClickComic(position: Int) {
        val comic = viewModel.listComic.value!!.get(position)
        //viewModel.setComic(comic)
        findNavController().navigate(R.id.action_characterFragment_to_historiaFragment)
    }

    override fun onClickEvent(position: Int) {
        val event = viewModel.listEvent.value!!.get(position)
        //viewModel.setEvent(event)
        findNavController().navigate(R.id.action_characterFragment_to_eventFragment)
    }


    override fun onClickSerie(position: Int) {
        val serie = viewModel.listSerie.value!!.get(position)
        //viewModel.setSerie(serie)
        findNavController().navigate(R.id.action_characterFragment_to_serieFragment)
    }
}