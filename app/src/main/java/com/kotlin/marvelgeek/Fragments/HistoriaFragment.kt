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
import com.kotlin.marvelgeek.R
import com.kotlin.marvelgeek.ViewModel.MainViewModel
import com.kotlin.marvelgeek.models.ComicC
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_historia.view.*


class HistoriaFragment : Fragment(){

    private var comic: ComicC? = null
    private val viewModel: MainViewModel by activityViewModels()
    private var savedState: Bundle? = null
    private var createdStateInDestroyView = false
    private val SAVED_BUNDLE_TAG = "saved_bundle"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_historia, container, false)

        if (savedState != null) {
            comic = (savedState!!.getSerializable(SAVED_BUNDLE_TAG) as ComicC?)!!
        } else {
            var mBundle = Bundle()
            if (mBundle != null) {
                mBundle = requireArguments()
                comic = mBundle.getSerializable("comic") as ComicC
            }
        }

        (activity as AppCompatActivity).supportActionBar?.title = comic!!.title
        Picasso.get().load("${comic!!.thumbnail.path}.${comic!!.thumbnail.extension}")
            .fit()
            .into(view.comicActIv)

        if (comic!!.description.isNullOrEmpty())
            view.comicActTvBio.text = "Sorry, there is no description to this Character."
        else
            view.comicActTvBio.text = comic!!.description

        // Character--------------------------------------------------------------------------------------------------------------
        var adapterCharacterNew = CharacterAdapterNew()
        view.comicActRvCharacter.adapter = adapterCharacterNew
        view.comicActRvCharacter.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        view.comicActRvCharacter.setHasFixedSize(true)
        var errorCharacter = viewModel.getCharacterComic(comic!!.id)
        if (errorCharacter != null) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Server problem:")
            builder.setIcon(R.drawable.ic_info)
            builder.setMessage(errorCharacter)
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
        viewModel.listCharacterComics.observe(viewLifecycleOwner) {
            adapterCharacterNew.addListComic(it)
        }

        // Event--------------------------------------------------------------------------------------------------------------
        var adapterEventNew = EventAdapterNew()
        view.comicActRvEvents.adapter = adapterEventNew
        view.comicActRvEvents.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        view.comicActRvEvents.setHasFixedSize(true)
        var errorEvent = viewModel.getEventComic(comic!!.id)
        if (errorEvent != null) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Server problem:")
            builder.setIcon(R.drawable.ic_info)
            builder.setMessage(errorEvent)
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
        viewModel.listEventComics.observe(viewLifecycleOwner) {
            adapterEventNew.addListEvent(it)
        }

        // Creators--------------------------------------------------------------------------------------------------------------
        var adapterCreator = CreatorAdapter()
        view.comicActRvCreators.adapter = adapterCreator
        view.comicActRvCreators.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        view.comicActRvCreators.setHasFixedSize(true)
        var errorCreator = viewModel.getCreatorComic(comic!!.id)
        if (errorCreator != null) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Server problem:")
            builder.setIcon(R.drawable.ic_info)
            builder.setMessage(errorCreator)
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
        viewModel.listCreatorComics.observe(viewLifecycleOwner) {
            Log.i("Tag",it.toString())
            adapterCreator.addListCreator(it)
        }

        view.fromComicToHome.setOnClickListener {
            findNavController().navigate(R.id.action_historiaFragment_to_homeFragment2)
        }

        view.fromComicToFavorite.setOnClickListener {
            findNavController().navigate(R.id.action_historiaFragment_to_favoriteFragment)
        }

        view.fbQuizC.setOnClickListener {
            findNavController().navigate(R.id.action_historiaFragment_to_quizFragment)
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        savedState = saveState()
        createdStateInDestroyView = true
        comic = null
    }

    private fun saveState(): Bundle? {
        val state = Bundle()
        state.putSerializable(SAVED_BUNDLE_TAG, comic)
        return state
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (comic == null) {
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