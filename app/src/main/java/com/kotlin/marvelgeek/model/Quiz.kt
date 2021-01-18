package com.kotlin.marvelgeek.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
class Quiz(
    val id: Int,
    val pergunta: String,
    val respostas: @RawValue ArrayList<Resposta>
) : Parcelable {
    var respostaSelecionada: Resposta? = null
}

@Parcelize
class Resposta(
    val id: Int,
    val texto: String,
    var isCorreta: Boolean = false
) : Parcelable