package com.kotlin.marvelgeek.Fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.marvelgeek.Adapters.FavoriteAdapter
import com.kotlin.marvelgeek.R
import com.kotlin.marvelgeek.ViewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_favorite.view.*

class FavoriteFragment : Fragment(),
    FavoriteAdapter.ListenerOnClickFavorito {

    var adapter = FavoriteAdapter(this)
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.favorite)

        viewModel.getFavorite()
        adapter.listFavorite.clear()
        adapter.notifyAdapter()

        viewModel.listFavorite.observe(viewLifecycleOwner){
            adapter.addListFavorite(it)
        }

        view.fromFavoToHome.setOnClickListener {
            viewModel.listCharacter.value?.clear()
            findNavController().navigate(R.id.action_favoriteFragment_to_action_homeFragment2)
        }

        view.fbQuiz.setOnClickListener{
            findNavController().navigate(R.id.action_favoriteFragment_to_quizFragment)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rvFavorite.adapter = adapter
        rvFavorite.layoutManager = LinearLayoutManager(context)
        rvFavorite.setHasFixedSize(false)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onLongClickFavorito(position: Int) {

        val personagem = adapter.listFavorite[position]

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Delete")
        builder.setIcon(R.drawable.ic_delete)
        builder.setMessage("Do you want delete ${personagem.name} from Favorites?")
        builder.setPositiveButton("Yes"){dialog, which ->
            viewModel.removeFavoriteCharacter(personagem.id)
            viewModel.listFavorite.observe(viewLifecycleOwner){
                adapter.addListFavorite(it)
            }
            //adapter.notifyAdapter()
        }
        builder.setNegativeButton("No"){dialog,which ->
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun onClickFavorito(position: Int) {
        // Mudar fragment
        val bundle = Bundle()
        viewModel.character = MutableLiveData()
        val id = adapter.listFavorite[position].id
        viewModel.getOneCharacterById(id)
        viewModel.character.observe(viewLifecycleOwner){
            bundle.putSerializable("character",  it)
            arguments = bundle
            findNavController().navigate(R.id.action_favoriteFragment_to_characterFragment,bundle)
        }
    }
}