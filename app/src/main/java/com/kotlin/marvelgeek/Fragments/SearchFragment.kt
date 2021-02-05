package com.kotlin.marvelgeek.Fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.FacebookSdk
import com.google.firebase.auth.FirebaseAuth
import com.kotlin.marvelgeek.Adapters.SearchAdapter
import com.kotlin.marvelgeek.R
import com.kotlin.marvelgeek.ViewModel.MainViewModel
import com.kotlin.marvelgeek.models.Character
import com.kotlin.marvelgeek.models.CharacterAdapter
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_home.view.fbQuiz
import kotlinx.android.synthetic.main.fragment_search.view.*


class SearchFragment : Fragment(), SearchAdapter.OnClickItemListener {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var character: Character

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).supportActionBar?.show()
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var mBundle = Bundle()
        if (mBundle != null) {
            mBundle = requireArguments()
            character = mBundle.getSerializable("character") as Character
        }

        Log.i("SearchFrag",character.toString())

        (activity as AppCompatActivity).supportActionBar?.setTitle("Result")
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        val adapter = SearchAdapter(this)
        view.rvSearch.adapter = adapter
        view.rvSearch.layoutManager = LinearLayoutManager(activity)
        view.rvSearch.setHasFixedSize(true)

        adapter.addListCharacter(character)


        view.fromSearchToFavo.setOnClickListener{
            if(viewModel.user != null){
                findNavController().navigate(R.id.action_searchFragment_to_favoriteFragment)
            }else{
                viewModel.showToast(it.context,"To access Favorites, sign-in with an account.")
            }
        }

        view.fbQuiz.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_quizFragment)
        }

        view.fromSearchToHome.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_homeFragment2)
        }
        return view
    }

    override fun OnClickItem(position: Int) {
        val character = viewModel.search.value
        val bundle = Bundle()
        bundle.putSerializable("character", character)
        arguments = bundle
        findNavController().navigate(R.id.action_searchFragment_to_characterFragment,bundle)
    }
}