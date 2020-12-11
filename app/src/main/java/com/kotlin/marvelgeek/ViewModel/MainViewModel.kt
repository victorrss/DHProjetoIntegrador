package com.kotlin.marvelgeek.ViewModel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.marvelgeek.R
import com.kotlin.marvelgeek.model.Personagem
import com.kotlin.marvelgeek.models.Character
import com.kotlin.marvelgeek.models.Comic
import com.kotlin.marvelgeek.models.apiPrivateKey
import com.kotlin.marvelgeek.models.apiPublicKey
import com.kotlin.marvelgeek.services.Repository
import com.kotlin.marvelgeek.services.repository
import kotlinx.coroutines.launch
import java.math.BigInteger
import java.security.MessageDigest

class MainViewModel(repository: Repository): ViewModel() {

    val listCharacter = MutableLiveData<ArrayList<Character>>()
    val listFavorite = MutableLiveData<ArrayList<Personagem>>()
    val listComic = MutableLiveData<ArrayList<Comic>>()
    var auxListComic = arrayListOf<Comic>()

    // --------------------------- Tela Home ----------------------//
    // Personagem tela Home
    fun getCharacter(limit: Int, offset: Int): String?{
        var error: String? = null
        val ts = timeStamp()
        viewModelScope.launch {
            try {
                val resultado = repository.getResultCharacters(
                        limit,
                        offset,
                        ts,
                        apiPublicKey,
                        "${ts}$apiPrivateKey$apiPublicKey".md5()
                )
                listCharacter.value = resultado.data.results
            }catch (e: Exception){
                Log.e("getCharacter",e.toString())
            }
        }
        return error
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
                if(listComic.value == null)
                    listComic.value = arrayListOf(resultado.data.results[0])
                else
                    listComic.value!!.add(resultado.data.results[0])
                Log.i("getComic",listComic.value.toString())
            }catch (e: Exception){
                Log.e("getComic",e.toString())
                error = e.toString()
            }
        }
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
}