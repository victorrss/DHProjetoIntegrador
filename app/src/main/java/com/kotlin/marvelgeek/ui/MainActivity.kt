package com.kotlin.marvelgeek.ui

import android.content.res.AssetManager
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

        val am: AssetManager = applicationContext.assets
        val inputStream = am.open("Characters.txt")
        var line = listOf<String>()
        var colors = listOf<String>()

        inputStream.bufferedReader().useLines { lines ->
            lines.forEach {
                line = it.split('\t')
                colors = line[1].split(",")
                if (colors[0].length != 7)
                    viewModel.colors[line[0]] = "#0${colors[0].split("#")[1]}"
               else
                    viewModel.colors[line[0]] = colors[0]
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        auth.signOut()
    }
}