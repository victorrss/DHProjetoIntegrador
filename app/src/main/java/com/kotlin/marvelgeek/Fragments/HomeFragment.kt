package com.kotlin.marvelgeek.Fragments

import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.FacebookSdk
import com.facebook.FacebookSdk.getApplicationContext
import com.kotlin.marvelgeek.R
import com.kotlin.marvelgeek.ViewModel.MainViewModel
import com.kotlin.marvelgeek.models.CharacterAdapter
import kotlinx.android.synthetic.main.activity_character.view.*

import kotlinx.android.synthetic.main.activity_home.view.*
import kotlinx.android.synthetic.main.activity_home.view.fbQuiz
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class HomeFragment : Fragment(), CharacterAdapter.OnClickItemListener {
    var error: String? = null
    private val viewModel: MainViewModel by activityViewModels()


    private lateinit var adapter: CharacterAdapter

    override fun onAttach(context: Context) {
        error = viewModel.getCharacter(100, 0)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).supportActionBar?.show()
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        val inflater = inflater
        inflater.inflate(R.menu.searchbar_menu, menu)

        val manager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.ic_menu_search)
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
        if(viewModel.user == null)
            viewModel.showToast(getApplicationContext(),"Welcome: Visitor")
        else
            viewModel.showToast(getApplicationContext(),"Welcome: ${viewModel.user}")
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.app_name)
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)


        //Atualizando os valores da lista
        if (error != null){
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Server problem:")
            builder.setIcon(R.drawable.ic_info)
            builder.setMessage(error)
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        view.fromHomeToFavo.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment2_to_favoriteFragment)
        }

        view.fbQuiz.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment2_to_quizFragment)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.listCharacter.observe(viewLifecycleOwner){
            adapter.addListCharacter(it)
        }
        super.onViewCreated(view, savedInstanceState)
        adapter = CharacterAdapter(this)
        view.rvHome.adapter = adapter
        view.rvHome.layoutManager = LinearLayoutManager(activity)
        view.rvHome.setHasFixedSize(true)
    }

    override fun OnClickItem(position: Int) {
        val character = viewModel.listCharacter.value!![position]
        val bundle = Bundle()
        bundle.putSerializable("character", character)
        arguments = bundle
        findNavController().navigate(R.id.action_homeFragment2_to_characterFragment)
    }
}