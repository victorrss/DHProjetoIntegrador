package com.kotlin.marvelgeek.Fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.marvelgeek.FavoriteAdapter
import com.kotlin.marvelgeek.R
import com.kotlin.marvelgeek.model.Personagem
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : Fragment(),
    FavoriteAdapter.ListenerOnClickFavorito {

    var listaFavorito = getAllFavorites()
    var adapter = FavoriteAdapter(listaFavorito, this)



    override fun onDestroyView() {
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.app_name)

        super.onDestroyView()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.toolbarFavoritosTitutlo)
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rvFavorite.adapter = adapter
        rvFavorite.layoutManager = LinearLayoutManager(context)
        rvFavorite.setHasFixedSize(false)
    }

    companion object{
        fun newInstance() = FavoriteFragment()
    }



    override fun onLongClickFavorito(position: Int) {

        val name = listaFavorito[position].nome

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Excluir")
        builder.setIcon(R.drawable.ic_delete)
        builder.setMessage("Você deseja excluir $name da sua lista de favoritos?")
        builder.setPositiveButton("Sim"){dialog, which ->
            listaFavorito.removeAt(position)
            adapter.notification(name)
        }
        builder.setNegativeButton("Não"){dialog,which ->
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun onClickFavorito(position: Int) {
        // Mudar fragment
        findNavController().navigate(R.id.action_favoriteFragment_to_characterFragment)
    }

    // Retorna uma lista de favoritos fixa
    fun getAllFavorites() = arrayListOf<Personagem>(Personagem(
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
            ))


}