package com.kotlin.marvelgeek.Fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.marvelgeek.Adapters.*
import com.kotlin.marvelgeek.Entities.EventC
import com.kotlin.marvelgeek.Entities.SerieC
import com.kotlin.marvelgeek.R
import com.kotlin.marvelgeek.ViewModel.MainViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_event.view.*
import kotlinx.android.synthetic.main.fragment_event.view.eventActIv
import kotlinx.android.synthetic.main.fragment_event.view.eventActTvBio
import kotlinx.android.synthetic.main.fragment_serie.view.*

class SerieFragment : Fragment() {

    private var serie: SerieC? = null
    private val viewModel: MainViewModel by activityViewModels()
    private var savedState: Bundle? = null
    private var createdStateInDestroyView = false
    private val SAVED_BUNDLE_TAG = "saved_bundle"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_serie, container, false)

        if (savedState != null) {
            serie = (savedState!!.getSerializable(SAVED_BUNDLE_TAG) as SerieC?)!!
        } else {
            var mBundle = Bundle()
            if (mBundle != null) {
                mBundle = requireArguments()
                serie = mBundle.getSerializable("serie") as SerieC
            }
        }

        (activity as AppCompatActivity).supportActionBar?.title = serie!!.title
        Picasso.get().load("${serie!!.thumbnail.path}.${serie!!.thumbnail.extension}")
            .fit()
            .into(view.serieActIv)

        if (serie!!.description.isNullOrEmpty())
            view.serieActTvBio.text = "Sorry, there is no description to this Character."
        else
            view.serieActTvBio.text = serie!!.description

        Log.i("Tag",serie!!.id.toString())

        // Character--------------------------------------------------------------------------------------------------------------
        var adapterCharacterNew = CharacterAdapterNew()
        view.serieRvCharacter.adapter = adapterCharacterNew
        view.serieRvCharacter.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        view.serieRvCharacter.setHasFixedSize(true)
        var errorCharacter = viewModel.getCharacterSerie(serie!!.id)
        if (errorCharacter != null) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Server problem:")
            builder.setIcon(R.drawable.ic_info)
            builder.setMessage(errorCharacter)
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
        viewModel.listCharactersSerie.observe(viewLifecycleOwner) {
            adapterCharacterNew.addListComic(it)
        }

        // Comic--------------------------------------------------------------------------------------------------------------
        var adapterComicNew = ComicAdapterNew()
        view.serieActRvComic.adapter = adapterComicNew
        view.serieActRvComic.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        view.serieActRvComic.setHasFixedSize(true)
        var errorComic = viewModel.getComicSerie(serie!!.id)
        if (errorComic != null) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Server problem:")
            builder.setIcon(R.drawable.ic_info)
            builder.setMessage(errorComic)
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
        viewModel.listComicsSerie.observe(viewLifecycleOwner) {
            adapterComicNew.addListComic(it)
        }

        // Event--------------------------------------------------------------------------------------------------------------
        var adapterEventNew = EventAdapterNew()
        view.serieActRvEvents.adapter = adapterEventNew
        view.serieActRvEvents.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        view.serieActRvEvents.setHasFixedSize(true)
        var errorEvent = viewModel.getEventSerie(serie!!.id)
        if (errorEvent != null) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Server problem:")
            builder.setIcon(R.drawable.ic_info)
            builder.setMessage(errorEvent)
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
        viewModel.listEventsSerie.observe(viewLifecycleOwner) {
            adapterEventNew.addListEvent(it)
        }

        // Creators--------------------------------------------------------------------------------------------------------------
        var adapterCreator = CreatorAdapter()
        view.serieActRvCreators.adapter = adapterCreator
        view.serieActRvCreators.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        view.serieActRvCreators.setHasFixedSize(true)
        var errorCreator = viewModel.getCreatorSerie(serie!!.id)
        if (errorCreator != null) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Server problem:")
            builder.setIcon(R.drawable.ic_info)
            builder.setMessage(errorCreator)
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
        viewModel.listCreatorsSerie.observe(viewLifecycleOwner) {
            adapterCreator.addListCreator(it)
        }

        view.fromSerieToHome.setOnClickListener {
            findNavController().navigate(R.id.action_serieFragment_to_homeFragment2)
        }

        view.fromSerieToFavorite.setOnClickListener {
            if(viewModel.user != null){
                findNavController().navigate(R.id.action_serieFragment_to_favoriteFragment)
            }else{
                viewModel.showToast(it.context,"To access Favorites, sign-in with an account.")
            }
        }

        view.fbQuizS.setOnClickListener {
            findNavController().navigate(R.id.action_serieFragment_to_quizFragment)
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        savedState = saveState()
        createdStateInDestroyView = true
        serie = null
    }

    private fun saveState(): Bundle? {
        val state = Bundle()
        state.putSerializable(SAVED_BUNDLE_TAG, serie)
        return state
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (serie == null) {
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