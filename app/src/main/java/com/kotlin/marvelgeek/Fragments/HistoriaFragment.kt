package com.kotlin.marvelgeek.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.kotlin.marvelgeek.R
import com.kotlin.marvelgeek.ViewModel.MainViewModel
import com.kotlin.marvelgeek.models.ComicC
import com.kotlin.marvelgeek.models.CreatorComic
import com.kotlin.marvelgeek.models.ItemComic
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_historia.*
import kotlinx.android.synthetic.main.layout_historia.view.*


class HistoriaFragment : Fragment() {


    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_historia, container, false)
        var autor: ItemComic? = null
        val comic =  viewModel.comic.value!!
        (activity as AppCompatActivity).supportActionBar?.setTitle("Marvel Geek")


        Picasso.get().load("${comic.thumbnail.path}.${comic.thumbnail.extension}")
            .fit()
            .into(view.imComic)
        view.tvComic.text = comic.title
        view.tvComicDescription.text = comic.description
        comic.creators.items.forEach {
            if(it.role == "penciller")
                view.tvPenciller.text = it.name
            else if(it.role == "writer") {
                view.tvWriter.text = it.name
                autor = it
            }
        }

        view.tvWriter.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("author", autor)
            arguments = bundle
            findNavController().navigate(R.id.action_historiaFragment_to_authorFragment,bundle)
        }
        return view
    }


}