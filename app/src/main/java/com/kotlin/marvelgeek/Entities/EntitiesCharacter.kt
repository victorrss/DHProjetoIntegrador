package com.kotlin.marvelgeek.models

import android.graphics.Bitmap
import java.io.Serializable
import kotlin.collections.ArrayList


data class ResCharacter(val code: Int,
                        val data: DataCharacter)

data class DataCharacter(val results: ArrayList<Character>)

data class Character(val id: Long,
                     val name: String,
                     val description: String,
                     val urls: ArrayList<URL>,
                     val thumbnail: Images,
                     val comics: ComicCharacter,
                     val stories: StoryCharacter,
                     val events: EventCharacter,
                     val series: SerieCharacter,
                     var color: String?): Serializable

data class URL(val type: String,
               val url: String): Serializable

data class ComicCharacter(val available: Int,
                          val items: ArrayList<ItemCharacter>): Serializable

data class StoryCharacter(val available: Int,
                          val items: ArrayList<ItemCharacter>): Serializable

data class EventCharacter(val available: Int,
                          val items: ArrayList<ItemCharacter>): Serializable

data class SerieCharacter(val available: Int,
                          val items: ArrayList<ItemCharacter>): Serializable

data class ItemCharacter(val resourceURI: String,
                         val name: String,
                         val type: String): Serializable

data class Images(val path: String, val extension: String): Serializable