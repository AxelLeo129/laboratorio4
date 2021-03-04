package com.example.laboratorio4

import android.app.Activity.*
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buscar: Button = findViewById(R.id.button)
        var imagen_pokemon = findViewById<ImageView>(R.id.PokemonImage)
        val uiHandler = Handler(Looper.getMainLooper())
        uiHandler.post(Runnable {
            Picasso.get().load("https://cdn2.iconfinder.com/data/icons/font-awesome/1792/search-512.png").into(imagen_pokemon)
        })

        buscar.setOnClickListener {
            val texto: EditText = findViewById(R.id.editText)
            var texto_error = findViewById<TextView>(R.id.TextErrors)
            var texto_view: String = "${texto.text}".toLowerCase()
            if(!texto_view.equals("")) {
                makeApiRequest(texto_view)
                texto.clearFocus()
                texto_error.text = ""
            } else {
                texto_error.text = "Por favor ingrese un nombre"
            }
        }

    }

    private fun hide(editText: EditText) {
        editText.setOnKeyListener(View.OnKeyListener{v, keyCode, event ->
            if((keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP )) {
                return@OnKeyListener true
            }
            false
        })
    }

    private fun makeApiRequest(nombre_pokemon: String) {
        Log.e("Main", "Error: ${nombre_pokemon}")
        val api = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/pokemon/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            var imagen_pokemon = findViewById<ImageView>(R.id.PokemonImage)
            var experiencia_base_pokemon = findViewById<TextView>(R.id.PokemonExpBase)
            var altura_pokemon = findViewById<TextView>(R.id.PokemonHeight)
            var peso_pokemon = findViewById<TextView>(R.id.PokemonWeight)
            var nombre_text_pokemon = findViewById<TextView>(R.id.PokemonName)
            try {
                val response = api.getPokemonByName("$nombre_pokemon/")
                val uiHandler = Handler(Looper.getMainLooper())
                uiHandler.post(Runnable {
                    Picasso.get().load(response.sprites.front_default).into(imagen_pokemon)
                })
                nombre_text_pokemon.text = "Nombre del Pokémon: ${response.name}"
                experiencia_base_pokemon.text = "Experiencia base: ${response.base_experience}"
                altura_pokemon.text = "Altura: ${response.height}"
                peso_pokemon.text = "Peso: ${response.weight}"
            } catch (e: Exception) {
                val uiHandler = Handler(Looper.getMainLooper())
                uiHandler.post(Runnable {
                    Picasso.get().load("https://images.vexels.com/media/users/3/153978/isolated/preview/483ef8b10a46e28d02293a31570c8c56-icono-de-trazo-de-color-de-se-ntilde-al-de-advertencia-by-vexels.png").into(imagen_pokemon)
                })
                nombre_text_pokemon.text = "Pokémon no encontrado, \npor favor verifique el nombre."
                experiencia_base_pokemon.text = ""
                altura_pokemon.text = ""
                peso_pokemon.text = ""
                Log.e("Main", "Error: ${e.message}")
            }
        }
    }

}