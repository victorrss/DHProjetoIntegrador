package com.kotlin.marvelgeek.Entities

import com.kotlin.marvelgeek.models.Images
import java.io.Serializable

data class ResCreator(val code: Int,
                    val data: DataCreator)

data class DataCreator(val results: ArrayList<CreatorID>)

data class CreatorID(val id: Long,
                     val fullName: String,
                     val modified: String,
                     val thumbnail: Images,
                     val comics: ComicCreator): Serializable

data class ComicCreator(val available: Int,
                        val items: ArrayList<ItemComic>): Serializable

data class ItemComic(val resourceURI: String,
                     val name: String): Serializable