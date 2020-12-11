package com.kotlin.marvelgeek.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.kotlin.marvelgeek.Entities.SerieC
import com.kotlin.marvelgeek.R
import com.kotlin.marvelgeek.models.ComicC
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_series.view.*
import kotlinx.android.synthetic.main.fragment_event.view.*
import kotlinx.android.synthetic.main.layout_historia.view.*
import java.text.SimpleDateFormat
import java.util.*

class SerieFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_serie, container, false)
        var mBundle =  Bundle()
        if(mBundle != null) {
            mBundle = requireArguments()
        }
        val serie =  mBundle.getSerializable("serie") as SerieC
        (activity as AppCompatActivity).supportActionBar?.setTitle("Marvel Geek")

        if (serie.modified != null){
            val date: Date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(serie.modified)
            val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.US)
            val stringDate = dateFormat.format(date)
            view.tvSerieAtualizacao.append(stringDate)
        }

        Picasso.get().load("${serie.thumbnail.path}.${serie.thumbnail.extension}")
            .fit()
            .into(view.imSerie)
        view.tvTituloSerie.text = serie.title
        view.tvDescricaoSerie.text = serie.description

        return view
    }
}