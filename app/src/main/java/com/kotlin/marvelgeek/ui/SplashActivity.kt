package com.kotlin.marvelgeek.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        //Handler(Looper.getMainLooper()).postDelayed({val intent = Intent(this, MainActivity::class.java)
        //    startActivity(intent)
        //    finish()}, 3500)
        startNewActivity(Intent(this,MainActivity::class.java))
        finish()
    }

    private fun startNewActivity(intent: Intent){
        startActivity(intent)
    }
}