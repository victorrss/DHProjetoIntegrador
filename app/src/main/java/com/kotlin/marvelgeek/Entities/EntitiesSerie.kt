package com.kotlin.marvelgeek.Entities

import com.kotlin.marvelgeek.models.Images
import com.kotlin.marvelgeek.models.URL
import java.io.Serializable

data class ResSerie(val code: Int,
                   val data: DataSerie)

data class DataSerie(val results: ArrayList<SerieC>)

data class SerieC(val id: Long,
                  val title: String,
                  val description: String,
                  val urls: ArrayList<URL>,
                  val thumbnail: Images,
                  val modified: String,
                  val characters: CharacterSerie,
                  val stories: StorySerie,
                  val comics: ComicSerie,
                  val events: EventSerie,
                  val creators: CreatorSerie): Serializable

data class CharacterSerie(val available: Int,
                          val items: ArrayList<ItemSerie>): Serializable

data class StorySerie(val available: Int,
                      val items: ArrayList<ItemSerie>): Serializable

data class ComicSerie(val available: Int,
                      val items: ArrayList<ItemSerie>): Serializable

data class EventSerie(val resourceURI: String,
                      val name: String): Serializable

data class CreatorSerie(val available: Int,
                        val items: ArrayList<ItemSerie>): Serializable

data class ItemSerie(val resourceURI: String,
                     val name: String,
                     val role: String,
                     val type: String): Serializable