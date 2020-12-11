package com.kotlin.marvelgeek.models

import java.io.Serializable
import kotlin.collections.ArrayList


data class ResComic(val code: Int,
                    val data: DataComic)

    data class DataComic(val results: ArrayList<Comic>)

data class Comic(val id: Int,
                 val title: String,
                 val description: String,
                 val urls: ArrayList<URL>,
                 val thumbnail: Images,
                 val character: CharacterComic,
                 val stories: StoryComic,
                 val events: EventComic,
                 val series: SerieComic,
                 val creator: CreatorComic): Serializable

data class CharacterComic(val available: Int,
                          val items: ArrayList<ItemComic>): Serializable

data class StoryComic(val available: Int,
                          val items: ArrayList<ItemComic>): Serializable

data class EventComic(val available: Int,
                          val items: ArrayList<ItemComic>): Serializable

data class SerieComic(val resourceURI: String,
                      val name: String): Serializable

data class CreatorComic(val available: Int,
                        val items: ArrayList<ItemComic>): Serializable

data class ItemComic(val resourceURI: String,
                       val name: String,
                       val role: String,
                       val type: String): Serializable