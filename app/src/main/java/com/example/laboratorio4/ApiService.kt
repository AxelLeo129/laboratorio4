package com.example.laboratorio4

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET
    fun getPokemonByName(@Url url: String): Response<PokemonResponse>
}
