package com.kotlin.marvelgeek.Entities

import com.kotlin.marvelgeek.models.Images
import com.kotlin.marvelgeek.models.URL
import java.io.Serializable
import kotlin.collections.ArrayList


data class ResStory(val code: Int,
                    val data: DataStory)

data class DataStory(val results: ArrayList<StoryC>)

data class StoryC(val id: Long,
                  val title: String,
                  val description: String,
                  val urls: ArrayList<URL>,
                  val thumbnail: Images,
                  val character: CharacterStory,
                  val comics: ComicStory,
                  val events: EventStory,
                  val series: SerieStory,
                  val creators: CreatorStory): Serializable

data class CharacterStory(val available: Int,
                          val items: ArrayList<ItemStory>): Serializable

data class ComicStory(val available: Int,
                      val items: ArrayList<ItemStory>): Serializable

data class EventStory(val available: Int,
                      val items: ArrayList<ItemStory>): Serializable

data class SerieStory(val resourceURI: String,
                      val name: String): Serializable

data class CreatorStory(val available: Int,
                        val items: ArrayList<ItemStory>): Serializable

data class ItemStory(val resourceURI: String,
                     val name: String,
                     val role: String,
                     val type: String): Serializable