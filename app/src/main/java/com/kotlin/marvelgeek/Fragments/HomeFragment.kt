package com.kotlin.marvelgeek.Fragments

import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.icu.number.NumberFormatter.with
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.marvelgeek.R
import com.kotlin.marvelgeek.ViewModel.MainViewModel
import com.kotlin.marvelgeek.models.CharacterAdapter
import com.kotlin.marvelgeek.services.repository
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.view.*
import java.lang.Exception


@Suppress("DEPRECATION")
class HomeFragment : Fragment(), CharacterAdapter.OnClickItemListener {
    var error: String? = null
    val viewModel by viewModels<MainViewModel>{
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(repository) as T
            }
        }
    }

    private lateinit var adapter: CharacterAdapter
    fun createPaletteSync(bitmap: Bitmap): Palette = Palette.from(bitmap).generate()

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).supportActionBar?.show()
         error = viewModel.getCharacter(30, 2)

        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        val inflater = inflater
        inflater.inflate(R.menu.searchbar_menu, menu)

        val manager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.search_icon_menu)
        val searchView = searchItem?.actionView as androidx.appcompat.widget.SearchView
        searchView.queryHint = getString(R.string.foundCharacter) + "..."
        searchView.setSearchableInfo(manager.getSearchableInfo(requireActivity().componentName))

        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
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

        adapter = CharacterAdapter(this)
        view.rvHome.adapter = adapter
        view.rvHome.setLayoutManager(LinearLayoutManager(activity))
        view.rvHome.setHasFixedSize(true)

        viewModel.listCharacter.observe(viewLifecycleOwner){

            adapter.addListCharacter(it)
        }

        //Atualizando os valores da lista
        if (error != null){
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Server problem:")
           // builder.setIcon(R.drawable.ic_info)
            builder.setMessage(error)
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        view.abHome.setNavigationOnClickListener{
            //findNavController().navigate(R.id.action_homeFragment_to_favoriteFragment)
        }

        view.fbQuiz.setOnClickListener {
            //findNavController().navigate(R.id.action_homeFragment_to_quizFragment)
        }

        return view
    }

    override fun OnClickItem(position: Int) {
        val character = viewModel.listCharacter.value!!.get(position)
        val bundle = Bundle()
        bundle.putSerializable("character", character)
        arguments = bundle
        findNavController().navigate(R.id.action_homeFragment2_to_characterFragment, bundle)
    }
}