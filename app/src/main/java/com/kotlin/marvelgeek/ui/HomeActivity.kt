package com.kotlin.marvelgeek.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.marvelgeek.R
import com.kotlin.marvelgeek.model.Personagem
import com.kotlin.marvelgeek.Adapters.PersonagemAdapter
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity(), PersonagemAdapter.OnClickItemListener {
    private lateinit var personagens: List<Personagem>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        personagens = getPersonagens()
        val adapter = PersonagemAdapter(personagens, this)

        rvHome.adapter = adapter
        rvHome.setLayoutManager(LinearLayoutManager(this))
    }

    override fun OnClickItem(position: Int) {
        val personagem = personagens[position]
        Toast.makeText(this, personagem.nome, Toast.LENGTH_SHORT).show()
    }

    private fun getPersonagens(): List<Personagem> {
        return listOf(
            Personagem(
                1,
                R.drawable.spiderman,
                "Homem-Aranha",
                "Todos as descrições das pessoas são sobre a humanidade do atendimento, a pessoa pega no pulso, examina, olha com carinho. Então eu acho que vai ter outra coisa, que os médicos cubanos trouxeram pro brasil, um alto grau de humanidade."
            ),
            Personagem(
                2,
                R.drawable.batman,
                "Batman",
                "Ai você fala o seguinte: \"- Mas vocês acabaram isso?\" Vou te falar: -\"Não, está em andamento!\" Tem obras que \"vai\" durar pra depois de 2010. Agora, por isso, nós já não desenhamos, não começamos a fazer projeto do que nós \"podêmo fazê\"? 11, 12, 13, 14... Por que é que não?"
            ),
            Personagem(
                3,
                R.drawable.hulk,
                "Hulk",
                "No meu xinélo da humildade eu gostaria muito de ver o Neymar e o Ganso. Por que eu acho que.... 11 entre 10 brasileiros gostariam. Você veja, eu já vi, parei de ver. Voltei a ver, e acho que o Neymar e o Ganso têm essa capacidade de fazer a gente olhar.\n"
            ),
            Personagem(
                1,
                R.drawable.spiderman,
                "Homem-Aranha",
                "Todos as descrições das pessoas são sobre a humanidade do atendimento, a pessoa pega no pulso, examina, olha com carinho. Então eu acho que vai ter outra coisa, que os médicos cubanos trouxeram pro brasil, um alto grau de humanidade."
            ),
            Personagem(
                2,
                R.drawable.batman,
                "Batman",
                "Ai você fala o seguinte: \"- Mas vocês acabaram isso?\" Vou te falar: -\"Não, está em andamento!\" Tem obras que \"vai\" durar pra depois de 2010. Agora, por isso, nós já não desenhamos, não começamos a fazer projeto do que nós \"podêmo fazê\"? 11, 12, 13, 14... Por que é que não?"
            ),
            Personagem(
                3,
                R.drawable.hulk,
                "Hulk",
                "No meu xinélo da humildade eu gostaria muito de ver o Neymar e o Ganso. Por que eu acho que.... 11 entre 10 brasileiros gostariam. Você veja, eu já vi, parei de ver. Voltei a ver, e acho que o Neymar e o Ganso têm essa capacidade de fazer a gente olhar.\n"
            ),
            Personagem(
                1,
                R.drawable.spiderman,
                "Homem-Aranha",
                "Todos as descrições das pessoas são sobre a humanidade do atendimento, a pessoa pega no pulso, examina, olha com carinho. Então eu acho que vai ter outra coisa, que os médicos cubanos trouxeram pro brasil, um alto grau de humanidade."
            ),
            Personagem(
                2,
                R.drawable.batman,
                "Batman",
                "Ai você fala o seguinte: \"- Mas vocês acabaram isso?\" Vou te falar: -\"Não, está em andamento!\" Tem obras que \"vai\" durar pra depois de 2010. Agora, por isso, nós já não desenhamos, não começamos a fazer projeto do que nós \"podêmo fazê\"? 11, 12, 13, 14... Por que é que não?"
            ),
            Personagem(
                3,
                R.drawable.hulk,
                "Hulk",
                "No meu xinélo da humildade eu gostaria muito de ver o Neymar e o Ganso. Por que eu acho que.... 11 entre 10 brasileiros gostariam. Você veja, eu já vi, parei de ver. Voltei a ver, e acho que o Neymar e o Ganso têm essa capacidade de fazer a gente olhar.\n"
            )
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.searchbar_menu, menu)

        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.search_icon_menu)
        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint = getString(R.string.str_buscar_personagem) + "..."
        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                searchView.setQuery("", false)
                searchItem.collapseActionView()
                Toast.makeText(this@HomeActivity, "Query: ${query}", Toast.LENGTH_LONG).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }
}