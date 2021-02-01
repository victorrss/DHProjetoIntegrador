package com.kotlin.marvelgeek.ViewModel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.facebook.CallbackManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.kotlin.marvelgeek.Entities.CreatorID
import com.kotlin.marvelgeek.Entities.EventC
import com.kotlin.marvelgeek.Entities.SerieC
import com.kotlin.marvelgeek.R
import com.kotlin.marvelgeek.model.Comic
import com.kotlin.marvelgeek.model.Event
import com.kotlin.marvelgeek.model.Personagem
import com.kotlin.marvelgeek.model.Serie
import com.kotlin.marvelgeek.models.Character
import com.kotlin.marvelgeek.models.ComicC
import com.kotlin.marvelgeek.models.apiPrivateKey
import com.kotlin.marvelgeek.models.apiPublicKey
import com.kotlin.marvelgeek.services.Repository
import com.kotlin.marvelgeek.services.repository
import com.kotlin.marvelgeek.ui.MainActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import java.math.BigInteger
import java.security.MessageDigest

class MainViewModel(repository: Repository): ViewModel() {

    lateinit var auth: FirebaseAuth
    lateinit var gso: GoogleSignInOptions
    lateinit var db: FirebaseFirestore
    lateinit var collectColor: CollectionReference
    lateinit var collectFavorites: CollectionReference
    val callbackManager = CallbackManager.Factory.create()
    var user: Any? = null
    val RC_SIGN_IN = 0

    val colors: MutableMap<String, ArrayList<String>> = hashMapOf()
    val listCharacter = MutableLiveData<ArrayList<Character>>()
    val listFavorite = MutableLiveData<ArrayList<Personagem>>()
    val listComic = MutableLiveData<ArrayList<ComicC>>()
    val listEvent = MutableLiveData<ArrayList<EventC>>()
    val listSerie = MutableLiveData<ArrayList<SerieC>>()
    val author = MutableLiveData<CreatorID>()
    val charecter = MutableLiveData<Character>()
    val comic = MutableLiveData<ComicC>()
    val event = MutableLiveData<EventC>()
    val serie = MutableLiveData<SerieC>()

    init{
        initDb()
        collectColor.get().addOnSuccessListener { result ->
            for (document in result) {
                colors[document.id] = arrayListOf(document["primaryColor"].toString(),document["secondaryColor"].toString())
            }
        }.addOnFailureListener { exception ->
                Log.d("init", "Error getting documents: ${exception}")
        }
    }


    // --------------------------- Tela Home ----------------------//
    // Initialize Firebase
    fun initAuth(){
        auth = FirebaseAuth.getInstance()
    }

    // GSO
    fun initGSO(){
        // GOOGLE SIGN-IN --------------------------------------------------------------------------
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
    }

    // --------------------------- Tela Home ----------------------//
    // Inicia Databse
    fun initDb(){
        db = FirebaseFirestore.getInstance()
        collectColor = db.collection("colors")
        collectFavorites = db.collection("favorites")
    }

    // Pega Cor
    fun getcolor(name: String): ArrayList<String>?{
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
                    it.colors = getcolor(it.name)
                }
                listCharacter.value = list
            }catch (e: Exception){
                error = e.toString()
            }
        }
        return error
    }



    fun setCharacter(character: Character) {
        charecter.value = character
    }
    fun setComic(comicC: ComicC) {
        comic.value = comicC
    }
    fun setEvent(eventC: EventC) {
        event.value = eventC
    }
    fun setSerie(serieC: SerieC) {
        serie.value = serieC
    }

    // Personagem tela Favorito (Database)
    fun getFavorite(): String?{
        var error: String? = null
        viewModelScope.launch {
            try{
                listFavorite.value = getAllFavorites()
                //Log.i("getFavorite",listCharacter.value.toString())
            }catch (e: java.lang.Exception){
                Log.e("getFavorite",e.toString())
                error = e.toString()
            }
        }
        return error
    }

    fun removeFavorite(position: Int){
        listFavorite.value!!.removeAt(position)
        Log.i("TAG",listFavorite.value.toString())
    }

    // --------------------------- Tela Character ----------------------//
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

    // --------------------------- Tela Autor ----------------------//
    fun getAutor(id: Long): String?{
        var error: String? = null
        val ts = timeStamp()
        viewModelScope.launch {
            try {
                val resultado = repository.getResultCreator(
                    id,
                    ts,
                    apiPublicKey,
                    "${ts}$apiPrivateKey$apiPublicKey".md5()
                )
                author.value = resultado.data.results[0]
                Log.i("View Model",author.value.toString())
            }catch (e: Exception){
                error = e.toString()
                Log.e("TAG",e.toString())
            }
        }
        Log.i("View Model","finalizando")
        return error
    }

    // TimeStamp
    fun timeStamp(): String{
        val tsLong = System.currentTimeMillis()/1000
        //Log.i("TimeStamp",tsLong.toString())
        return tsLong.toString()
    }

    // MD5
    fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        //Log.i("MD5",BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0'))
        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
    }

    fun createPaletteSync(bitmap: Bitmap): Palette = Palette.from(bitmap).generate()


    // Retorna uma lista de favoritos fixa
    fun getAllFavorites() = arrayListOf(Personagem(
        1,
        R.drawable.spiderman,
        "Homem-Aranha",
        "Todos as descrições das pessoas são sobre a humanidade do atendimento, a pessoa pega no pulso, examina, olha com carinho. Então eu acho que vai ter outra coisa, que os médicos cubanos trouxeram pro brasil, um alto grau de humanidade."
    ),
        Personagem(
            2,
            R.drawable.juggernaut,
            "Juggernaut",
            "Ai você fala o seguinte: \"- Mas vocês acabaram isso?\" Vou te falar: -\"Não, está em andamento!\" Tem obras que \"vai\" durar pra depois de 2010. Agora, por isso, nós já não desenhamos, não começamos a fazer projeto do que nós \"podêmo fazê\"? 11, 12, 13, 14... Por que é que não?"
        ),
        Personagem(
            3,
            R.drawable.hulk,
            "Hulk",
            "No meu xinélo da humildade eu gostaria muito de ver o Neymar e o Ganso. Por que eu acho que.... 11 entre 10 brasileiros gostariam. Você veja, eu já vi, parei de ver. Voltei a ver, e acho que o Neymar e o Ganso têm essa capacidade de fazer a gente olhar.\n"
        ),
        Personagem(
            1,
            R.drawable.spiderman,
            "Homem-Aranha",
            "Todos as descrições das pessoas são sobre a humanidade do atendimento, a pessoa pega no pulso, examina, olha com carinho. Então eu acho que vai ter outra coisa, que os médicos cubanos trouxeram pro brasil, um alto grau de humanidade."
        ))

    fun showToast(context: Context,msg:String){
        Toast.makeText(context,msg, Toast.LENGTH_LONG).show()
    }
}