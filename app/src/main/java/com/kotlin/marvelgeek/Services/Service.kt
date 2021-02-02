package com.kotlin.marvelgeek.services

import com.kotlin.marvelgeek.Entities.ResCreator
import com.kotlin.marvelgeek.Entities.ResEvent
import com.kotlin.marvelgeek.Entities.ResSerie
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

    @GET("characters/{id}")
    suspend fun getResultOneCharacter(
        @Path("id") p1: Long,
        @Query("ts") p2: String,
        @Query("apikey") p3: String,
        @Query("hash") p4: String
    ): ResCharacter

    @GET("characters/{id}/comics")
    suspend fun getResultComics(
        @Path("id") p1: Long,
        @Query("ts") p2: String,
        @Query("apikey") p3: String,
        @Query("hash") p4: String
    ): ResComic

    @GET("characters/{id}/events")
    suspend fun getResultEvents(
        @Path("id") p1: Long,
        @Query("ts") p2: String,
        @Query("apikey") p3: String,
        @Query("hash") p4: String
    ): ResEvent

    @GET("characters/{id}/series")
    suspend fun getResultSeries(
        @Path("id") p1: Long,
        @Query("ts") p2: String,
        @Query("apikey") p3: String,
        @Query("hash") p4: String
    ): ResSerie

    @GET("creators/{id}")
    suspend fun getResultCreator(
        @Path("id") p1: Long,
        @Query("ts") p2: String,
        @Query("apikey") p3: String,
        @Query("hash") p4: String
    ): ResCreator
}

val urlApiMarvel = "https://gateway.marvel.com/v1/public/"

val retrofit = Retrofit.Builder()
    .baseUrl(urlApiMarvel)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val repository: Repository = retrofit.create(Repository::class.java)