package com.kotlin.marvelgeek.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.kotlin.marvelgeek.Entities.EventC
import com.kotlin.marvelgeek.R
import com.kotlin.marvelgeek.models.ComicC
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_character.view.*
import kotlinx.android.synthetic.main.activity_series.view.*
import kotlinx.android.synthetic.main.fragment_event.view.*
import java.text.SimpleDateFormat
import java.util.*

class EventFragment : Fragment() {

    // listaHerois: List<String> = getListaHerois()
    // listaCriadores: List<String> = getListaCriadores()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_event, container, false)
        var mBundle =  Bundle()
        if(mBundle != null) {
            mBundle = requireArguments()
        }
        (activity as AppCompatActivity).supportActionBar?.setTitle("Marvel Geek")
        val event =  mBundle.getSerializable("event") as EventC

        Picasso.get().load("${event.thumbnail.path}.${event.thumbnail.extension}")
            .fit()
            .into(view.ivEvent)
        if (event.modified != null){
            val date: Date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(event.modified)
            val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.US)
            val stringDate = dateFormat.format(date)
            view.tvEventAtualizacao.append(stringDate)
        }

        event.characters.items.forEach {
            view.tvListaHerois.append("-" + it.name + "\n")
        }

        event.creators.items.forEach{
            view.tvListaCriadores.append("-" + it.name + "\n")
        }

        return view
    }
}