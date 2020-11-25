package com.kotlin.marvelgeek.Fragments

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.marvelgeek.R
import com.kotlin.marvelgeek.model.Personagem
import com.kotlin.marvelgeek.model.PersonagemAdapter
import kotlinx.android.synthetic.main.activity_home.view.*


    class HomeFragment : Fragment(), PersonagemAdapter.OnClickItemListener {
        private lateinit var personagens: List<Personagem>



        override fun onCreate(savedInstanceState: Bundle?) {
            (activity as AppCompatActivity).supportActionBar?.show()
            setHasOptionsMenu(true)
            super.onCreate(savedInstanceState)
        }

        override fun OnClickItem(position: Int) {
            val personagem = personagens[position]
            Toast.makeText(activity, personagem.nome, Toast.LENGTH_SHORT).show()
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

        override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

            val inflater = inflater
            inflater.inflate(R.menu.searchbar_menu, menu)

            val manager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
            val searchItem = menu?.findItem(R.id.search_icon_menu)
            val searchView = searchItem?.actionView as androidx.appcompat.widget.SearchView
            searchView.queryHint = getString(R.string.str_buscar_personagem) + "..."
            searchView.setSearchableInfo(manager.getSearchableInfo(requireActivity().componentName))

            searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchView.clearFocus()
                    searchView.setQuery("", false)
                    searchItem.collapseActionView()
                    Toast.makeText(activity, "Query: ${query}", Toast.LENGTH_LONG).show()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })

            super.onCreateOptionsMenu(menu, inflater)
        }


        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            val view = inflater.inflate(R.layout.fragment_home, container, false)

            personagens = getPersonagens()
            val adapter = PersonagemAdapter(personagens, this)
            view.rvHome.adapter = adapter
            view.rvHome.setLayoutManager(LinearLayoutManager(activity))

            view.abHome.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment2_to_favoriteFragment)
            }


            return view

        }
    }

