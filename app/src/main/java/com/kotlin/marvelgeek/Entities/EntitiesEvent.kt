package com.kotlin.marvelgeek.Entities

import com.kotlin.marvelgeek.models.Images
import com.kotlin.marvelgeek.models.URL
import java.io.Serializable

data class ResEvent(val code: Int,
                    val data: DataEvent)

data class DataEvent(val results: ArrayList<EventC>)

data class EventC(val id: Long,
                  val title: String,
                  val description: String,
                  val urls: ArrayList<URL>,
                  val thumbnail: Images,
                  val characters: CharacterEvent,
                  val stories: StoryEvent,
                  val comics: ComicEvent,
                  val series: SerieEvent,
                  val creators: CreatorEvent): Serializable

data class CharacterEvent(val available: Int,
                          val items: ArrayList<ItemEvent>): Serializable

data class StoryEvent(val available: Int,
                      val items: ArrayList<ItemEvent>): Serializable

data class ComicEvent(val available: Int,
                      val items: ArrayList<ItemEvent>): Serializable

data class SerieEvent(val resourceURI: String,
                      val name: String): Serializable

data class CreatorEvent(val available: Int,
                        val items: ArrayList<ItemEvent>): Serializable

data class ItemEvent(val resourceURI: String,
                     val name: String,
                     val role: String,
                     val type: String): Serializable