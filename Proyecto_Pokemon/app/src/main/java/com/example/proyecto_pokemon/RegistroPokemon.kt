package com.example.proyecto_pokemon

import android.app.AlertDialog
import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.example.proyecto_pokemo.Entrenador
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_registro_pokemon.*
import java.io.BufferedReader
import java.io.File
import java.io.IOException

class RegistroPokemon : AppCompatActivity() {

    private var jsonInicial: String? = null

    var listaTipos = arrayOf("Agua", "Fuego", "Planta")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_pokemon)

        inputTipo.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listaTipos )

        try {
        botonGuardarPokemon.setOnClickListener {
            tomarInputs()
            agregarRegistroAJson(id, nombre, nivel, tipo, listaAtaques)
            mostrarMensaje("Pokemon Registrado")
        } }
        catch(e: IOException) {
            mostrarMensaje(e.toString())
        }

    }




    private fun agregarRegistroAJson(Id: Int, Nombre: String, Nivel: Int, Tipo: String, Ataques: ArrayList<String> ) {
        leerJson()
        var post = Pokemon(Id, Nombre, Nivel, Tipo, Ataques)
        var gson = Gson()

        var jsonString: String = gson.toJson(post)
        var jsonString2: String = jsonInicial.toString()
        var jsonFinal = jsonString2 + "," + jsonString.toString()
        val file = File("/data/data/com.example.proyecto_pokemon/files/Pokemons.json")
        file.writeText(jsonFinal)
        guardarFormatoJson(jsonFinal)

    }


    private fun leerJson() {
        val bufferedReader: BufferedReader =
            File("/data/data/com.example.proyecto_pokemon/files/Pokemons.json").bufferedReader()
        val inputString = bufferedReader.use { it.readText() }
        jsonInicial = inputString
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

    private fun guardarFormatoJson(json: String){

        val jsonFormatoFinal= "[" + json.toString() + "]"
        val file = File("/data/data/com.example.proyecto_pokemon/files/PokemonsJSON.json")
        file.writeText(jsonFormatoFinal)

    }

    private fun tomarInputs() {
        val et_idInput = findViewById<TextInputEditText>(R.id.idInputPokemon)
        val et_nombreInput = findViewById<TextInputEditText>(R.id.idNombrePokemon)
        val et_nivelInput = findViewById<EditText>(R.id.inputNivel)
        val et_tipoInput = findViewById<Spinner>(R.id.inputTipo)


        id = et_idInput.text.toString().toInt()
        nombre = et_nombreInput.text.toString()
        nivel = et_nivelInput.text.toString().toInt()
        tipo = et_tipoInput.selectedItem.toString()
        listaAtaques.add("Takle")
        listaAtaques.add("Giro")
        listaAtaques.add("Correr")

    }





    private var id = 0
    private var nombre=""
    private var tipo =""
    private var nivel = 0
    private var listaAtaques = arrayListOf<String>()




}
