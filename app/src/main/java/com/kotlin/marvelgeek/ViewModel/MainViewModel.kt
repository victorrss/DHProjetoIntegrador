package com.kotlin.marvelgeek.ViewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.kotlin.marvelgeek.Entities.CreatorID
import com.kotlin.marvelgeek.Entities.EventC
import com.kotlin.marvelgeek.Entities.SerieC
import com.kotlin.marvelgeek.model.Personagem
import com.kotlin.marvelgeek.models.Character
import com.kotlin.marvelgeek.models.ComicC
import com.kotlin.marvelgeek.models.apiPrivateKey
import com.kotlin.marvelgeek.models.apiPublicKey
import com.kotlin.marvelgeek.services.Repository
import com.kotlin.marvelgeek.services.repository
import kotlinx.coroutines.launch
import java.math.BigInteger
import java.security.MessageDigest

class MainViewModel(repository: Repository): ViewModel() {

    lateinit var auth: FirebaseAuth
    lateinit var gso: GoogleSignInOptions
    lateinit var db: FirebaseFirestore
    lateinit var collectColor: CollectionReference
    lateinit var collectFavorites: CollectionReference
    var user: Any? = null

    val colors: MutableMap<String, String> = hashMapOf()
    val listCharacter = MutableLiveData<ArrayList<Character>>()
    val listFavorite = MutableLiveData<ArrayList<Personagem>>()
    val listComic = MutableLiveData<ArrayList<ComicC>>()
    val listEvent = MutableLiveData<ArrayList<EventC>>()
    val listSerie = MutableLiveData<ArrayList<SerieC>>()

    // -> Tela Comic
    val listCharacterComics = MutableLiveData<ArrayList<Character>>()
    val listCreatorComics = MutableLiveData<ArrayList<CreatorID>>()
    val listEventComics = MutableLiveData<ArrayList<EventC>>()
    val listSerieComics = MutableLiveData<ArrayList<SerieC>>()

    init{
        var primary: String = ""

        initDb()

        collectColor.get().addOnSuccessListener { result ->
            for (document in result) {
                primary = if(document["primaryColor"].toString().length != 7)
                    "#0${document["primaryColor"].toString().split("#")[1]}"
                else
                    document["primaryColor"].toString()
                colors[document.id] = primary
            }
        }.addOnFailureListener { exception ->
                Log.d("init", "Error getting documents: ${exception}")
        }
    }

    // --------------------------- Tela Home ----------------------//
    // Inicia Databse
    fun initDb(){
        db = FirebaseFirestore.getInstance()
        collectFavorites = db.collection("userName")
        collectColor = db.collection("colors")
    }

    // Pega Cor
    fun getcolor(name: String): String?{
        return colors[name]
    }

    // Personagem tela Home
    fun getCharacter(limit: Int, offset: Int): String?{
        var error: String? = null
        val ts = timeStamp()
        var list = arrayListOf<Character>()

        viewModelScope.launch {
            try {
                val resultado = repository.getResultCharacters(
                        limit,
                        offset,
                        ts,
                        apiPublicKey,
                        "${ts}$apiPrivateKey$apiPublicKey".md5()
                )
                list = resultado.data.results
                list.forEach {
                    it.color = getcolor(it.name.replace("/"," "))
                }
                listCharacter.value = list
            }catch (e: Exception){
                error = e.toString()
            }
        }
        return error
    }

    // Personagem tela Home
    fun getOneCharacter(id: Long): Character{
        var error: String? = null
        val ts = timeStamp()
        var char = arrayListOf<Character>()

        viewModelScope.launch {
            try {
                val resultado = repository.getResultOneCharacter(
                    id,
                    ts,
                    apiPublicKey,
                    "${ts}$apiPrivateKey$apiPublicKey".md5()
                )
                char = resultado.data.results
                char[0].color = getcolor(char[0].name.replace("/"," "))
            }catch (e: Exception){
                error = e.toString()
            }
        }
        return char[0]
    }

    // Personagem tela Favorito (Database)
    fun getFavorite(){
        var list: ArrayList<Personagem> = arrayListOf()
        collectFavorites.get().addOnSuccessListener { result ->
                    for (document in result) {
                        list.add(
                            Personagem(
                                document["id"].toString().toLong(),
                                document["name"].toString(),
                                document["description"].toString(),
                                document["image"].toString(),
                                document["color"].toString(),
                                document["hsv"].toString().toFloat()
                            )
                        )
                    }
                listFavorite.value = list
                }.addOnFailureListener { exception ->
                    Log.d("Firestore", "Error getting documents: $exception")
                }

    }

    fun removeFavoriteCharacter(id: Long){
        collectFavorites.document(id.toString()).delete()
        getFavorite()
    }

    // --------------------------- Tela Character ----------------------//
    // Adicionar favorito ao DB
    fun addFavorite(character: Character){
        val characters: MutableMap<String,String> = HashMap()
        characters["id"] = character.id.toString()
        characters["name"] = character.name
        characters["description"] = character.description
        characters["image"] = character.thumbnail.path + "." + character.thumbnail.extension
        characters["color"] = character.color.toString()
        characters["hsv"] = character.brightness.toString()
        collectFavorites.document(character.id.toString()).set(characters)
        getFavorite()
        //collectFavorites.document(user.toString() + "/${character.id}").set(characters)
    }

    fun getComic(id: Long): String?{
        var error: String? = null
        val ts = timeStamp()
        viewModelScope.launch {
            try {
                val resultado = repository.getResultComics(
                    id,
                    ts,
                    apiPublicKey,
                    "${ts}$apiPrivateKey$apiPublicKey".md5()
                )
                listComic.value = resultado.data.results
                //Log.i("View Model",listComic.value.toString())
            }catch (e: Exception){
                //Log.e("getComic",e.toString())
                error = e.toString()
            }
        }
        return error
    }

    fun getEvent(id: Long): String?{
        var error: String? = null
        val ts = timeStamp()
        viewModelScope.launch {
            try {
                val resultado = repository.getResultEvents(
                    id,
                    ts,
                    apiPublicKey,
                    "${ts}$apiPrivateKey$apiPublicKey".md5()
                )
                listEvent.value = resultado.data.results
                //Log.i("View Model",listComic.value.toString())
            }catch (e: Exception){
                //Log.e("getEvent",e.toString())
                error = e.toString()
            }
        }
        return error
    }

    fun getSerie(id: Long): String?{
        var error: String? = null
        val ts = timeStamp()
        viewModelScope.launch {
            try {
                val resultado = repository.getResultSeries(
                    id,
                    ts,
                    apiPublicKey,
                    "${ts}$apiPrivateKey$apiPublicKey".md5()
                )
                listSerie.value = resultado.data.results
            }catch (e: Exception){
                Log.e("getSerie",e.toString())
                error = e.toString()
            }
        }
        return error
    }
    // ***********************************************************************************************************
    // --------------------------- Tela Comic ----------------------//
    fun getCharacterComic(id: Long): String?{
        var error: String? = null
        val ts = timeStamp()
        viewModelScope.launch {
            try {
                val resultado = repository.getResultCharacterComics(
                    id,
                    ts,
                    apiPublicKey,
                    "${ts}$apiPrivateKey$apiPublicKey".md5()
                )
                listCharacterComics.value = resultado.data.results
            }catch (e: Exception){
                error = e.toString()
                Log.e("TAG",e.toString())
            }
        }
        return error
    }

    fun getEventComic(id: Long): String?{
        var error: String? = null
        val ts = timeStamp()
        viewModelScope.launch {
            try {
                val resultado = repository.getResultEventsComic(
                    id,
                    ts,
                    apiPublicKey,
                    "${ts}$apiPrivateKey$apiPublicKey".md5()
                )
                listEventComics.value = resultado.data.results
            }catch (e: Exception){
                error = e.toString()
            }
        }
        return error
    }

    fun getCreatorComic(id: Long): String?{
        var error: String? = null
        val ts = timeStamp()
        viewModelScope.launch {
            try {
                val resultado = repository.getResultCreatorComic(
                    id,
                    ts,
                    apiPublicKey,
                    "${ts}$apiPrivateKey$apiPublicKey".md5()
                )
                listCreatorComics.value = resultado.data.results
                Log.i("TAG",resultado.data.results.toString())
            }catch (e: Exception){
                error = e.toString()
                Log.e("TAG",e.toString())
            }
        }
        return error
    }

    // TimeStamp
    fun timeStamp(): String{
        val tsLong = System.currentTimeMillis()/1000
        return tsLong.toString()
    }

    // MD5
    fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
    }

    fun showToast(context: Context,msg:String){
        Toast.makeText(context,msg, Toast.LENGTH_LONG).show()
    }
}