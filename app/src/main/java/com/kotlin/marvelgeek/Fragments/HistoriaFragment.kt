package com.kotlin.marvelgeek.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.kotlin.marvelgeek.R
import com.kotlin.marvelgeek.models.ComicC
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_historia.*
import kotlinx.android.synthetic.main.layout_historia.view.*


class HistoriaFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_historia, container, false)
        var mBundle =  Bundle()
        if(mBundle != null) {
            mBundle = requireArguments()
        }
        val comic =  mBundle.getSerializable("comic") as ComicC
        (activity as AppCompatActivity).supportActionBar?.setTitle("Marvel Geek")


        Picasso.get().load("${comic.thumbnail.path}.${comic.thumbnail.extension}")
            .fit()
            .into(view.imComic)
        view.tvComic.text = comic.title
        view.tvComicDescription.text = comic.description
        comic.creators.items.forEach {
            if(it.role == "penciller")
                view.tvPenciller.text = it.name
            else(it.role == "writer")
                view.tvWriter.text = it.name
        }

        return view
    }


}