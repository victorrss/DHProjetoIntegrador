package com.kotlin.marvelgeek.Fragments

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.marvelgeek.Adapters.ComicAdapter
import com.kotlin.marvelgeek.Adapters.EventAdapter
import com.kotlin.marvelgeek.Adapters.SerieAdapter
import com.kotlin.marvelgeek.R
import com.kotlin.marvelgeek.ViewModel.MainViewModel
import com.kotlin.marvelgeek.model.Personagem
import com.kotlin.marvelgeek.models.Character
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_character.view.*


class CharacterFragment : Fragment(), ComicAdapter.onClickListenerComic,
    EventAdapter.onClickListenerEvent,
    SerieAdapter.onClickListenerSerie{

    var character: Character? = null
    private val viewModel: MainViewModel by activityViewModels()
    private var savedState: Bundle? = null
    private var createdStateInDestroyView = false
    private val SAVED_BUNDLE_TAG = "saved_bundle"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_character, container, false)

        if (savedState != null) {
            character = savedState!!.getSerializable(SAVED_BUNDLE_TAG) as Character?
        }else {
            var mBundle = Bundle()
            if (mBundle != null) {
                mBundle = requireArguments()
                character = mBundle.getSerializable("character") as Character
            }
        }

        if(character!!.color != null){
            setColor(view, character!!.color!!,character!!.brightness)
        }

        (activity as AppCompatActivity).supportActionBar?.title = character!!.name
        Picasso.get().load("${character!!.thumbnail.path}.${character!!.thumbnail.extension}")
            .fit()
            .into(view.chaActIvHero)

        if (character!!.description.isNullOrEmpty())
            view.chaActTvBio.text = "Sorry, there is no description to this Character."
        else
            view.chaActTvBio.text = character!!.description

        // Comic--------------------------------------------------------------------------------------------------------------
        var adapterComic = ComicAdapter(this)
        view.chaActRvComics.adapter = adapterComic
        view.chaActRvComics.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL , false)
        view.chaActRvComics.setHasFixedSize(true)
        var errorComic = viewModel.getComic(character!!.id)
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
        var errorEvent = viewModel.getEvent(character!!.id)
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
        var errorSerie = viewModel.getSerie(character!!.id)
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

        viewModel.getFavorite()
        viewModel.listFavorite.observe(viewLifecycleOwner){
            if(!it.isNullOrEmpty()){
                if (it.contains(
                        Personagem(
                        character!!.id,
                        character!!.name,
                        character!!.description,
                        "${character!!.thumbnail.path}.${character!!.thumbnail.extension}",
                        character!!.color,
                        character!!.brightness)
                    ))
                    view.icFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
            }
        }

        view.icFavorite.setOnClickListener {
           //if(viewModel.user != null) {
                if (view.icFavorite.drawable.constantState == resources.getDrawable(R.drawable.ic_favorite).constantState) {
                    Log.i("Favorito","Adicionando")
                    viewModel.addFavorite(character!!)
                    viewModel.showToast(view.context,"${character!!.name} adicionado aos favoritos.")
                    view.icFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                } else {
                    Log.i("Favorito","Removendo")
                    viewModel.removeFavoriteCharacter(character!!)
                    viewModel.showToast(view.context,"${character!!.name} removido dos favoritos.")
                    view.icFavorite.setImageResource(R.drawable.ic_favorite)
                }
            //}else{
            //    viewModel.showToast(view.context,"Para favoritar, entre com uma conta.")
            //}
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
        val comic = viewModel.listComic.value!![position]
        val bundle = Bundle()
        bundle.putSerializable("comic", comic)
        arguments = bundle
        findNavController().navigate(R.id.action_characterFragment_to_historiaFragment,bundle)
    }

    override fun onClickEvent(position: Int) {
        val event = viewModel.listEvent.value!![position]
        val bundle = Bundle()
        bundle.putSerializable("event", event)
        arguments = bundle
        findNavController().navigate(R.id.action_characterFragment_to_eventFragment,bundle)
    }


    override fun onClickSerie(position: Int) {
        val serie = viewModel.listSerie.value!![position]
        val bundle = Bundle()
        bundle.putSerializable("serie", serie)
        arguments = bundle
        findNavController().navigate(R.id.action_characterFragment_to_serieFragment,bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        savedState = saveState()
        createdStateInDestroyView = true
        character = null
    }

    private fun saveState(): Bundle? {
        val state = Bundle()
        state.putSerializable(SAVED_BUNDLE_TAG, character)
        return state
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (character == null) {
            outState.putBundle(SAVED_BUNDLE_TAG, savedState)
        } else {
            outState.putBundle(
                SAVED_BUNDLE_TAG,
                if (createdStateInDestroyView) savedState else saveState()
            )
        }
        createdStateInDestroyView = false
        super.onSaveInstanceState(outState)
    }


}