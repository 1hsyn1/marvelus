package com.huseyinbulbul.marvelus.common.network

import com.huseyinbulbul.marvelus.common.data.Character
import com.huseyinbulbul.marvelus.common.data.Comic
import com.huseyinbulbul.marvelus.common.data.Comics
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {
    @GET("/v1/public/characters?limit=30&orderBy=name")
    fun getCharacters(@Query("offset") offset: Int): Observable<MarvelResponse<MarvelListData<Character>>>

    @GET("/v1/public/comics?dateRange=2005-01-01%2C2025-01-01&orderBy=-onsaleDate&limit=10")
    fun getComics(@Query("characters") characterId: Int): Observable<MarvelResponse<MarvelListData<Comic>>>
}