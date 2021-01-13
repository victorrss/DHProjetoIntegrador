package com.kotlin.marvelgeek.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kotlin.marvelgeek.R
import kotlinx.android.synthetic.main.fragment_quiz.view.*

class QuizFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_quiz, container, false)
        view.quiFraBtnNext.setOnClickListener {
            findNavController().navigate(R.id.action_quizFragment_to_quizResultFragment)
        }
        return view
    }
}