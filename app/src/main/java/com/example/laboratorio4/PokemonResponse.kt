package com.example.laboratorio4

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    @SerializedName("name") var nombre: String,
    @SerializedName("order") var numero: Int,
    @SerializedName("height") var altura: Int,
    @SerializedName("weight") var peso: Int
    )
