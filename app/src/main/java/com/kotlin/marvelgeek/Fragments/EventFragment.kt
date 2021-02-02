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
import com.kotlin.marvelgeek.R
import com.kotlin.marvelgeek.ViewModel.MainViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_event.view.*
import kotlinx.android.synthetic.main.fragment_historia.view.*

class EventFragment : Fragment() {

    private var event: EventC? = null
    private val viewModel: MainViewModel by activityViewModels()
    private var savedState: Bundle? = null
    private var createdStateInDestroyView = false
    private val SAVED_BUNDLE_TAG = "saved_bundle"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_event, container, false)

        if (savedState != null) {
            event = (savedState!!.getSerializable(SAVED_BUNDLE_TAG) as EventC?)!!
        } else {
            var mBundle = Bundle()
            if (mBundle != null) {
                mBundle = requireArguments()
                event = mBundle.getSerializable("event") as EventC
            }
        }

        (activity as AppCompatActivity).supportActionBar?.title = event!!.title
        Picasso.get().load("${event!!.thumbnail.path}.${event!!.thumbnail.extension}")
            .fit()
            .into(view.eventActIv)

        if (event!!.description.isNullOrEmpty())
            view.eventActTvBio.text = "Sorry, there is no description to this Character."
        else
            view.eventActTvBio.text = event!!.description

        Log.i("Tag",event!!.id.toString())

        // Character--------------------------------------------------------------------------------------------------------------
        var adapterCharacterNew = CharacterAdapterNew()
        view.eventActRvCharacter.adapter = adapterCharacterNew
        view.eventActRvCharacter.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        view.eventActRvCharacter.setHasFixedSize(true)
        var errorCharacter = viewModel.getCharacterEvent(event!!.id)
        if (errorCharacter != null) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Server problem:")
            builder.setIcon(R.drawable.ic_info)
            builder.setMessage(errorCharacter)
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
        viewModel.listCharactersEvent.observe(viewLifecycleOwner) {
            adapterCharacterNew.addListComic(it)
        }

        // Comic--------------------------------------------------------------------------------------------------------------
        var adapterComicNew = ComicAdapterNew()
        view.eventActRvComic.adapter = adapterComicNew
        view.eventActRvComic.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        view.eventActRvComic.setHasFixedSize(true)
        var errorComic = viewModel.getComicEvent(event!!.id)
        if (errorComic != null) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Server problem:")
            builder.setIcon(R.drawable.ic_info)
            builder.setMessage(errorComic)
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
        viewModel.listComicsEvent.observe(viewLifecycleOwner) {
            adapterComicNew.addListComic(it)
        }

        // Serie--------------------------------------------------------------------------------------------------------------
        var adapterSerieNew = SerieAdapterNew()
        view.eventActRvSeries.adapter = adapterSerieNew
        view.eventActRvSeries.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        view.eventActRvSeries.setHasFixedSize(true)
        var errorSerie = viewModel.getSerieEvent(event!!.id)
        if (errorSerie != null) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Server problem:")
            builder.setIcon(R.drawable.ic_info)
            builder.setMessage(errorSerie)
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
        viewModel.listSerieComics.observe(viewLifecycleOwner) {
            adapterSerieNew.addListSerie(it)
        }

        // Creators--------------------------------------------------------------------------------------------------------------
        var adapterCreator = CreatorAdapter()
        view.eventActRvCreators.adapter = adapterCreator
        view.eventActRvCreators.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        view.eventActRvCreators.setHasFixedSize(true)
        var errorCreator = viewModel.getCreatorEvent(event!!.id)
        if (errorCreator != null) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Server problem:")
            builder.setIcon(R.drawable.ic_info)
            builder.setMessage(errorCreator)
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
        viewModel.listCreatorsEvent.observe(viewLifecycleOwner) {
            adapterCreator.addListCreator(it)
        }

        view.fromEventToHome.setOnClickListener {
            findNavController().navigate(R.id.action_eventFragment_to_homeFragment2)
        }

        view.fromEventToFavorite.setOnClickListener {
            findNavController().navigate(R.id.action_eventFragment_to_favoriteFragment)
        }

        view.fbQuizE.setOnClickListener {
            findNavController().navigate(R.id.action_eventFragment_to_quizFragment)
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        savedState = saveState()
        createdStateInDestroyView = true
        event = null
    }

    private fun saveState(): Bundle? {
        val state = Bundle()
        state.putSerializable(SAVED_BUNDLE_TAG, event)
        return state
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (event == null) {
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