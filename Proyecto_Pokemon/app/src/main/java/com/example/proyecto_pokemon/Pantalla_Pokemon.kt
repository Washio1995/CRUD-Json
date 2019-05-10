package com.example.proyecto_pokemon

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_pantalla__entrenador.*
import kotlinx.android.synthetic.main.activity_pantalla__pokemon.*
import org.json.JSONArray
import java.io.File
import java.io.IOException
import java.io.InputStream

class Pantalla_Pokemon : AppCompatActivity() {

    object index {
        var indexSeleccionado:Int? = null
    }


    var arrListaPokemon = arrayListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla__pokemon)
        listarJsonArray()

        botonNuevoPokemon.setOnClickListener {
            val intent = Intent(this, RegistroPokemon::class.java)
            startActivity(intent)
        }

        listViewPokemon.setOnItemClickListener { parent, view, position, id ->
            Pantalla_Pokemon.index.indexSeleccionado = listViewPokemon.getItemIdAtPosition(position).toString().toInt()
            val intent = Intent(this, AccionPokemon::class.java)
            startActivity(intent)
        }

    }

    fun listarJsonArray() {
        var json: String? = null

        try {
            val inputStream: InputStream = File("/data/data/com.example.proyecto_pokemon/files/Pokemons.json").inputStream()
            json = inputStream.bufferedReader().use { it.readText() }
            val jsonCompleto: String = "[" + json.toString() + "]"
            var jsonarr = JSONArray(jsonCompleto)

            for (i in 0..jsonarr.length()-1) {
                var jsonobj = jsonarr.getJSONObject(i)
                arrListaPokemon.add(jsonobj.getString("nombre"))
            }
            var adpt = ArrayAdapter(this, android.R.layout.simple_list_item_1,arrListaPokemon)
            listViewPokemon.adapter = adpt
        }
        catch (e: IOException) {
            println(e.toString())
        }
    }
}
