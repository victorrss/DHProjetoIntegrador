package com.kotlin.marvelgeek

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_event.*

class EventActivity : AppCompatActivity() {

    val listaHerois: List<String> = getListaHerois()
    val listaCriadores: List<String> = getListaCriadores()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        tvEventAtualizacao.append(" 04/01/2008")

        listaHerois.forEach{
            tvListaHerois.append("-" + it + "\n")
        }

        listaCriadores.forEach{
            tvListaCriadores.append("-" + it + "\n")
        }

        tbEvento.setNavigationOnClickListener {
            finish()
        }
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