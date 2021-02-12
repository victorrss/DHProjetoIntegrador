package com.kotlin.marvelgeek.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.marvelgeek.Adapters.SearchAdapter
import com.kotlin.marvelgeek.R
import com.kotlin.marvelgeek.ViewModel.MainViewModel
import com.kotlin.marvelgeek.models.Character
import kotlinx.android.synthetic.main.fragment_home.view.fbQuiz
import kotlinx.android.synthetic.main.fragment_search.view.*


class SearchFragment : Fragment(), SearchAdapter.OnClickItemListener {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var characters: ArrayList<Character>
    val adapter = SearchAdapter(this)

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
            try {
                characters = mBundle.getSerializable("characters")!! as ArrayList<Character>
            } catch (e: Exception) {
                Log.e("SearchFragment", e.toString())
            }
        }

        (activity as AppCompatActivity).supportActionBar?.setTitle("Result")
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        view.rvSearch.adapter = adapter
        view.rvSearch.layoutManager = LinearLayoutManager(activity)
        view.rvSearch.setHasFixedSize(true)
        viewModel.clearSearch()

        adapter.addListCharacter(characters)


        view.fromSearchToFavo.setOnClickListener {
            if (viewModel.user != null) {
                findNavController().navigate(R.id.action_searchFragment_to_favoriteFragment)
            } else {
                viewModel.showToast(it.context, "To access Favorites, sign-in with an account.")
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
        val character = adapter.listCharacter[position]
        Log.i("Search", adapter.listCharacter[position].toString())
        val bundle = Bundle()
        bundle.putSerializable("character", character)
        arguments = bundle
        findNavController().navigate(R.id.action_searchFragment_to_characterFragment, bundle)
    }
}