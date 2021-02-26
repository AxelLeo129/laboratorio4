package com.example.laboratorio4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buscar: Button = findViewById(R.id.button)

        buscar.setOnClickListener {
            val texto: EditText = findViewById(R.id.editText)
             searchByName("${texto.text}")
        }

    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/pokemon/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchByName(nombre: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getPokemonByName("$nombre/")
            val pokemon = call.body() as PokemonResponse
            if(call.isSuccessful) {
                showToast("Funciona " + pokemon.nombre)
            } else {
                showToast("No funciona")
            }
        }
    }

    private fun showToast(titulo: String) {
        Toast.makeText(this, titulo, Toast.LENGTH_SHORT).show()
    }

}