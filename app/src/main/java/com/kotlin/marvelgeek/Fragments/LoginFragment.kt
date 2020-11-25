package com.kotlin.marvelgeek.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kotlin.marvelgeek.R
import kotlinx.android.synthetic.main.activity_login.view.*


class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        view.btnLoginVisitante.setOnClickListener {
           findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }

        return view

    }

}