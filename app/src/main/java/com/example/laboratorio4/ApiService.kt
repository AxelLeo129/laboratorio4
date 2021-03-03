package com.example.laboratorio4

import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun getPokemonByName(@Url url: String): PokemonResponse
}
