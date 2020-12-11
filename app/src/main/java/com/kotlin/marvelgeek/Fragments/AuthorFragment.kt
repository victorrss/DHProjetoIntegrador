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
import com.kotlin.marvelgeek.R
import com.kotlin.marvelgeek.ViewModel.MainViewModel
import com.kotlin.marvelgeek.models.ComicC
import com.kotlin.marvelgeek.models.ItemComic
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_author.view.*
import kotlinx.android.synthetic.main.fragment_author.view.tvAuthorName
import kotlinx.android.synthetic.main.fragment_event.view.*
import java.text.SimpleDateFormat
import java.util.*


class AuthorFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_author, container, false)
        var mBundle =  Bundle()
        if(mBundle != null) {
            mBundle = requireArguments()
        }
        val autor =  mBundle.getSerializable("author") as ItemComic
        val id = autor.resourceURI.split("/")[autor.resourceURI.split("/").size - 1].toLong()
        (activity as AppCompatActivity).supportActionBar?.setTitle("Marvel Geek")

        var errorComic = viewModel.getAutor(id)
        Log.i("Passei","Service")
        if (errorComic != null){
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Server problem:")
            builder.setIcon(R.drawable.ic_info)
            builder.setMessage(errorComic)
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
        viewModel.author.observe(viewLifecycleOwner){
            Picasso.get().load("${it.thumbnail.path}.${it.thumbnail.extension}")
                .fit()
                .into(view.ivAuthor)
            view.tvAuthorName.text = it.fullName
            it.comics.items.forEach {
                view.tvListaQuadrinhos.append("-" + it.name + "\n")
            }

            if (it.modified != null){
                val date: Date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(it.modified)
                val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.US)
                val stringDate = dateFormat.format(date)
                view.tvAuthorAtualizacao.append(stringDate)
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}