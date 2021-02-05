package com.kotlin.marvelgeek.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.kotlin.marvelgeek.R
import com.kotlin.marvelgeek.ViewModel.MainViewModel
import com.kotlin.marvelgeek.ViewModel.MyViewModelFactory
import com.kotlin.marvelgeek.services.repository

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val viewModel: MainViewModel by viewModels {
        MyViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)

        auth = FirebaseAuth.getInstance()
        viewModel
    }

    override fun onBackPressed() {
        super.onBackPressed()

        auth.signOut()
    }
}