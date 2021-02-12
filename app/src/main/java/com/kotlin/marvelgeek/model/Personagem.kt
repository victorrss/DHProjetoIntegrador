package com.kotlin.marvelgeek.model

import java.io.Serializable

data class Personagem(val id: Long,
                     val name: String,
                     val description: String,
                     val thumbnail: String,
                     var color: String?,
                     var brightness: Float = 1.toFloat())