package com.kotlin.marvelgeek

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_author.*

class AuthorActivity : AppCompatActivity() {

    val listaQuadrinhos: List<String> = getListaQuadrinhos()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_author)

        tvAuthorAtualizacao.append(" 10/10/2020")


        listaQuadrinhos.forEach{
            tvListaQuadrinhos.append("-" + it + "\n")
        }

        tbAuthor.setNavigationOnClickListener {
            finish()
        }
    }

    private fun getListaQuadrinhos(): ArrayList<String>{
        return arrayListOf<String>("Spider-Man Unmasked","X-Man #31","Spider-Man Unlimited (Vol. 1) #22: Poisoned Souls")
    }
}