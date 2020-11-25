package com.kotlin.marvelgeek

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_author.view.*


class AuthorFragment : Fragment() {

    val listaQuadrinhos: List<String> = getListaQuadrinhos()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_author, container, false)

        view.tvAuthorAtualizacao.append(" 10/10/2020")


        listaQuadrinhos.forEach{
            view.tvListaQuadrinhos.append("-" + it + "\n")
        }

        return view
    }

    private fun getListaQuadrinhos(): ArrayList<String>{
        return arrayListOf<String>("Spider-Man Unmasked","X-Man #31","Spider-Man Unlimited (Vol. 1) #22: Poisoned Souls")
    }
}