package com.kotlin.marvelgeek.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.marvelgeek.Adapters.FavoriteAdapter
import com.kotlin.marvelgeek.R
import com.kotlin.marvelgeek.ViewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : Fragment(),
    FavoriteAdapter.ListenerOnClickFavorito {

    var adapter = FavoriteAdapter(this)
    private val viewModel: MainViewModel by activityViewModels()


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

//        val name = listaFavorito[position].nome
//
//        val builder = AlertDialog.Builder(context)
//        builder.setTitle("Excluir")
//        builder.setIcon(R.drawable.ic_delete)
//        builder.setMessage("Você deseja excluir $name da sua lista de favoritos?")
//        builder.setPositiveButton("Sim"){dialog, which ->
//            listaFavorito.removeAt(position)
//            adapter.notification(name)
//        }
//        builder.setNegativeButton("Não"){dialog,which ->
//        }
//        val dialog: AlertDialog = builder.create()
//        dialog.show()
    }

    override fun onClickFavorito(position: Int) {
        // Mudar fragment
        val bundle = Bundle()
        bundle.putSerializable("character", viewModel.getOneCharacter(viewModel.listFavorite.value!![position].id))
        arguments = bundle
        findNavController().navigate(R.id.action_favoriteFragment_to_characterFragment)
    }
}