package com.kotlin.marvelgeek

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        abHome.setNavigationOnClickListener {
            val frag = FavoriteFragment.newInstance()
            supportFragmentManager.beginTransaction().apply {
                add(R.id.homeFragment,frag)
                commit()
            }
        }
    }
}