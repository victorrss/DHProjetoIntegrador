package com.kotlin.marvelgeek.ui

import android.content.res.AssetManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import java.io.File
import java.io.InputStream


class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val cr = db.collection("colors")

        var line = listOf<String>()
        var colors = listOf<String>()
        val characters: MutableMap<String,String> = HashMap()
        //Log.i("PATH",applicationContext.assets.toString())

        val am: AssetManager = applicationContext.assets
        val inputStream = am.open("Characters.txt")

        //val inputStream: InputStream = File("Characters1.txt").inputStream()

        inputStream.bufferedReader().useLines { lines ->
            lines.forEach {
                line = it.split('\t')
                colors = line[1].split(",")
                characters["primaryColor"] = colors[0]
                characters["secondaryColor"] = colors[1]
                cr.document(line[0].replace('/',' ')).set(characters).addOnSuccessListener {
                }.addOnFailureListener {
                }
            }
        }
    }
}