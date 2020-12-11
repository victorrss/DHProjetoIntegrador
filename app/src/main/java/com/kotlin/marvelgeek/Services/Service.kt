package com.kotlin.marvelgeek.services

import com.kotlin.marvelgeek.models.ResCharacter
import com.kotlin.marvelgeek.models.ResComic
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Repository {

    @GET("characters")
    suspend fun getResultCharacters(
        @Query("limit") p2: Int,
        @Query("offset") p3: Int,
        @Query("ts") p4: String,
        @Query("apikey") p5: String,
        @Query("hash") p6: String
    ): ResCharacter

    @GET("comics/{id}")
    suspend fun getResultComics(
        @Path("id") p1: Long,
        @Query("ts") p2: String,
        @Query("apikey") p3: String,
        @Query("hash") p4: String
    ): ResComic
}

val urlApiMarvel = "https://gateway.marvel.com/v1/public/"

val retrofit = Retrofit.Builder()
    .baseUrl(urlApiMarvel)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val repository: Repository = retrofit.create(Repository::class.java)