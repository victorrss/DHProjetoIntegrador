package com.kotlin.marvelgeek.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.kotlin.marvelgeek.R
import com.kotlin.marvelgeek.ViewModel.MainViewModel

class SerieFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_serie, container, false)

//        val serie =  viewModel.serie.value!!
//        (activity as AppCompatActivity).supportActionBar?.setTitle("Marvel Geek")
//
//        if (serie.modified != null){
//            val date: Date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(serie.modified)
//            val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.US)
//            val stringDate = dateFormat.format(date)
//            view.tvSerieAtualizacao.append(stringDate)
//        }
//
//        Picasso.get().load("${serie.thumbnail.path}.${serie.thumbnail.extension}")
//            .fit()
//            .into(view.imSerie)
//        view.tvTituloSerie.text = serie.title
//        view.tvDescricaoSerie.text = serie.description

        return view
    }
}