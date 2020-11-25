package com.kotlin.marvelgeek

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_event.view.*

class EventFragment : Fragment() {

    val listaHerois: List<String> = getListaHerois()
    val listaCriadores: List<String> = getListaCriadores()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_event, container, false)
        view.tvEventAtualizacao.append(" 04/01/2008")

        listaHerois.forEach{
            view.tvListaHerois.append("-" + it + "\n")
        }

        listaCriadores.forEach{
            view.tvListaCriadores.append("-" + it + "\n")
        }

        return view
    }

    private fun getListaHerois(): ArrayList<String>{
        return arrayListOf<String>("Alpha Flight","Ancient One","Apocalypse","Archangel","Avengers",
                "Banshee","Beast","Beetle (Abner Jenkins)","Black Knight (Sir Percy of Scandia)","Bushwacker",
                "Captain America","Captain Marvel (Carol Danvers)","Doctor Strange")
    }

    private fun getListaCriadores(): ArrayList<String>{
        return arrayListOf<String>("Jeff Albrecht","Hilary Barta", "Bret Blevins","Danny Bulanadi","Craig Anderson",
                "Tom Brevoort","Bobbie Chase","Janice Chiang")
    }
}