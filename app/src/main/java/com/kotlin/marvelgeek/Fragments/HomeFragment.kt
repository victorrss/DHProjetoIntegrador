package com.kotlin.marvelgeek.Fragments

import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.FacebookSdk.getApplicationContext
import com.google.firebase.auth.FirebaseAuth
import com.kotlin.marvelgeek.R
import com.kotlin.marvelgeek.ViewModel.MainViewModel
import com.kotlin.marvelgeek.models.Character
import com.kotlin.marvelgeek.models.CharacterAdapter
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.coroutines.CoroutineScope


class HomeFragment : Fragment(), CharacterAdapter.OnClickItemListener {
    var error: String? = null
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var auth: FirebaseAuth
    var adapter = CharacterAdapter(this)

    private var offset: Int = 0
    private val visibleThreshold: Int = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).supportActionBar?.show()
        setHasOptionsMenu(true)
        offset = 0
        if(adapter.listCharacter.isNotEmpty())
            adapter.listCharacter.clear()
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
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
                viewModel.getOneCharacter(query.toString(),getApplicationContext())
                viewModel.search.observe(viewLifecycleOwner){
                    val bundle = Bundle()
                    Log.i("Search",it.toString())
                    bundle.putSerializable("character", it)
                    arguments = bundle
                    findNavController().navigate(R.id.action_homeFragment2_to_searchFragment,bundle)
                }
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

        if (!adapter.listCharacter.isNullOrEmpty())
            offset = adapter.listCharacter.size
        Log.i("OFFSET", offset.toString())
        error = viewModel.getCharacter(visibleThreshold, offset)

        if(viewModel.user == null)
            viewModel.showToast(getApplicationContext(),"Welcome: Guest")
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

        viewModel.listCharacter.observe(viewLifecycleOwner){
            adapter.addListCharacter(it)
        }
        view.rvHome.adapter = adapter
        view.rvHome.layoutManager = LinearLayoutManager(activity)
        view.rvHome.setHasFixedSize(true)
        view.rvHome.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if ((view.rvHome.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition() == adapter.listCharacter.size - 1) {
                    offset = adapter.listCharacter.size
                    viewModel.getCharacter(visibleThreshold,offset)
                }
            }
        })

        view.fromHomeToFavo.setOnClickListener{
            if(viewModel.user != null){
                findNavController().navigate(R.id.action_homeFragment2_to_favoriteFragment)
            }else{
                viewModel.showToast(it.context,"To access Favorites, sign-in with an account.")
            }
        }

        view.fbQuiz.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment2_to_quizFragment)
        }

        view.exitApp.setOnClickListener {
            auth.signOut()
            findNavController().navigate(R.id.action_homeFragment2_to_loginFragment)
        }
        return view
    }

    override fun OnClickItem(position: Int) {
        viewModel.listCharacter.value?.clear()
        val character = adapter.listCharacter[position]
        val bundle = Bundle()
        bundle.putSerializable("character", character)
        arguments = bundle
        findNavController().navigate(R.id.action_homeFragment2_to_characterFragment,bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.listCharacter.value?.clear()
    }
}