package com.example.proyecto_pokemon

import android.app.AlertDialog
import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_accion_pokemon.*
import kotlinx.android.synthetic.main.activity_registro_pokemon.*
import org.json.JSONArray
import java.io.BufferedReader
import java.io.File
import java.io.InputStream

class AccionPokemon : AppCompatActivity() {

    private var jsonInicial: String? = null

    var arrListaPokemonsFiltrados = arrayListOf<String>()

    var listaTipos = arrayOf("Agua", "Fuego", "Planta")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accion_pokemon)
        inputTipoPokemonAct.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listaTipos )

        establecerValoresEnPantalla()

        botonEliminarPokemon.setOnClickListener {
            filtrarJsonArray()
            eliminarRegistro()
            mostrarMensaje("Pokemon Eliminado")
        }

        botonActualizarPokemon.setOnClickListener {
            filtrarJsonArray()
            eliminarRegistro()
            tomarInputs()
            actualizarRegistroJson(id,nombre, nivel, tipo, listaAtaques)
            mostrarMensaje("Registro de Pokemon Actualizado")
        }
    }


    private fun leerJson() {
        var gson = Gson()
        val bufferedReader: BufferedReader =
            File("/data/data/com.example.proyecto_pokemon/files/Pokemons.json").bufferedReader()
        val inputString = bufferedReader.use { it.readText() }
        jsonInicial = inputString
    }

    private fun actualizarRegistroJson(Id: Int, Nombre: String, Nivel: Int, Tipo: String, Ataques: ArrayList<String>) {
        leerJson()
        var post = Pokemon(Id, Nombre, Nivel, Tipo, Ataques)
        var gson = Gson()
        var jsonString: String = gson.toJson(post)
        var jsonString2: String = jsonInicial.toString()
        var jsonFinal = jsonString2 + ',' + jsonString.toString()
        val file = File("/data/data/com.example.proyecto_pokemon/files/Pokemons.json")
        file.writeText(jsonFinal)
        guardarFormatoJson(jsonFinal)

    }

    private fun mostrarMensaje(mensaje: String) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage(mensaje)
            .setCancelable(false)
            .setPositiveButton("OK", DialogInterface.OnClickListener() { dialog, id ->
                finish()
            })

        val alert = dialogBuilder.create()
        alert.show()

    }

    private fun tomarInputs() {
        val et_idInput = findViewById<TextInputEditText>(R.id.inputIdPokemonAct)
        val et_nombreInput = findViewById<TextInputEditText>(R.id.inputNombrePokemonAct)
        val et_nivelInput = findViewById<EditText>(R.id.inputNivelPokemonAct)
        val et_tipoInput = findViewById<Spinner>(R.id.inputTipoPokemonAct)
        id = et_idInput.text.toString().toInt()
        nombre = et_nombreInput.text.toString()
        nivel = et_nivelInput.text.toString().toInt()
        tipo = et_tipoInput.selectedItem.toString()
        listaAtaques.add("Takle")
        listaAtaques.add("Giro")
        listaAtaques.add("Correr")
    }

    private fun filtrarJsonArray() {
        var json: String? = null
        val inputStream: InputStream = File("/data/data/com.example.proyecto_pokemon/files/Pokemons.json").inputStream()
        json = inputStream.bufferedReader().use { it.readText() }
        val jsonCompleto: String = "[" + json.toString() + "]"
        var jsonarr = JSONArray(jsonCompleto)

        for (i in 0..jsonarr.length()-1) {
            if( i == Pantalla_Pokemon.index.indexSeleccionado) {
            } else {
                arrListaPokemonsFiltrados.add(jsonarr.getJSONObject(i).toString())
            }
        }

    }

    private fun establecerValoresEnPantalla() {
        var json: String? = null
        val inputStream: InputStream = File("/data/data/com.example.proyecto_pokemon/files/Pokemons.json").inputStream()
        json = inputStream.bufferedReader().use { it.readText() }
        val jsonCompleto: String = "[" + json.toString() + "]"
        var jsonarr = JSONArray(jsonCompleto)

        for (i in 0..jsonarr.length()-1) {
            if( i == Pantalla_Pokemon.index.indexSeleccionado) {
                var jsonobj = jsonarr.getJSONObject(i)
                inputIdPokemonAct.setText(jsonobj.getString("id"))
                inputNombrePokemonAct.setText(jsonobj.getString("nombre"))
                inputNivelPokemonAct.setText(jsonobj.getString("nivel"))
            } else {
            }
        }
    }

    fun eliminarRegistro(){

        var jsonEliminado = ""

        for (i in 0..arrListaPokemonsFiltrados.size-2) {
            jsonEliminado = jsonEliminado + arrListaPokemonsFiltrados.get(i) + ","
        }
        jsonEliminado = jsonEliminado + arrListaPokemonsFiltrados.get(arrListaPokemonsFiltrados.size-1)

        val file = File("/data/data/com.example.proyecto_pokemon/files/Pokemons.json")
        file.writeText(jsonEliminado)
        guardarFormatoJson(jsonEliminado)
    }

    private fun guardarFormatoJson(json: String){

        val jsonFormatoFinal= "[" + json.toString() + "]"
        val file = File("/data/data/com.example.proyecto_pokemon/files/PokemonsJSON.json")
        file.writeText(jsonFormatoFinal)

    }











    private var id = 0
    private var nombre=""
    private var nivel=0
    private var tipo= ""
    private var listaAtaques = arrayListOf<String>()



}
