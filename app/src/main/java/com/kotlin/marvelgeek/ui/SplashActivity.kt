package com.kotlin.marvelgeek.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        startNewActivity(Intent(this,MainActivity::class.java))
        finish()
    }

    private fun startNewActivity(intent: Intent){
        startActivity(intent)
    }
}