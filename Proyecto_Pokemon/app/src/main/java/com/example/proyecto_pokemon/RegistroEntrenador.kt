package com.example.proyecto_pokemon

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_registro_entrenador.*
import android.app.AlertDialog
import android.content.DialogInterface
import android.support.design.widget.TextInputEditText
import android.widget.ArrayAdapter
import com.google.gson.Gson
import com.example.proyecto_pokemo.Entrenador
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_accion_entrenador.*
import kotlinx.android.synthetic.main.activity_pantalla__entrenador.*
import org.json.JSONArray
import java.io.*

class RegistroEntrenador : AppCompatActivity() {

    private var jsonInicial: String? = null
    private var arrListaPokemons = arrayListOf<String>()
    var stringLista: String? = null
    var jsonCompletoPokemon:String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_entrenador)

        listarPokemons()


        botonRegistrarEntrenador.setOnClickListener {

                tomarInputs()
                obtenerPokemon()
                agregarRegistroAJson(id, nombre, nacionalidad, jsonCompletoPokemon)
                mostrarMensaje("Entrenador Registrado")

        }

    }



    private fun agregarRegistroAJson(Id: Int, Nombre: String, Nacionalidad: String, ListaPokemon: String) {
        leerJson()

        var post = Entrenador(Id, Nombre, Nacionalidad, ListaPokemon)
        var gson = Gson()
        var jsonString: String = gson.toJson(post)
        var jsonString2: String = jsonInicial.toString()
        var jsonFinal =  jsonString2 + "," + jsonString.toString()
        val file = File("/data/data/com.example.proyecto_pokemon/files/Entrenadores.json")
        file.writeText(jsonFinal)
        guardarFormatoJson(jsonFinal)

    }


    private fun leerJson() {
        var gson = Gson()
        val bufferedReader: BufferedReader =
            File("/data/data/com.example.proyecto_pokemon/files/Entrenadores.json").bufferedReader()
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
        //println(jsonCompletoPokemon )
        val file = File("/data/data/com.example.proyecto_pokemon/files/EntrenadoresJSON.json")
        file.writeText(jsonFormatoFinal)

    }

    private fun tomarInputs() {
        val et_idInput = findViewById<TextInputEditText>(R.id.idInput)
        val et_nombreInput = findViewById<TextInputEditText>(R.id.nombreInput)
        val et_nacionalidadInput = findViewById<TextInputEditText>(R.id.paisOrigenInput)
        id = et_idInput.text.toString().toInt()
        nombre = et_nombreInput.text.toString()
        nacionalidad = et_nacionalidadInput.text.toString()

    }


    fun listarPokemons() {
        var json: String? = null

        try {
            val inputStream: InputStream = File("/data/data/com.example.proyecto_pokemon/files/Pokemons.json").inputStream()
            json = inputStream.bufferedReader().use { it.readText() }
            val jsonCompleto: String = "[" + json.toString() + "]"
            var jsonarr = JSONArray(jsonCompleto)

            for (i in 0..jsonarr.length()-1) {
                var jsonobj = jsonarr.getJSONObject(i)
                arrListaPokemons.add(jsonobj.getString("nombre"))
            }
            var adpt = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,arrListaPokemons)
            inputPokemon.adapter = adpt
        }
        catch (e: IOException) {
            println(e.toString())
        }
    }


    fun obtenerPokemon() {
        var json: String? = null
        val gson = Gson()
        val inputStream: InputStream = File("/data/data/com.example.proyecto_pokemon/files/Pokemons.json").inputStream()
        json = inputStream.bufferedReader().use { it.readText() }
        val jsonCompleto: String = "[" + json.toString() + "]"
        var jsonarr = JSONArray(jsonCompleto)

        for (i in 0..jsonarr.length()-1) {
            if( i == inputPokemon.selectedItemPosition) {
                var jsonobj = jsonarr.getJSONObject(i)
                jsonCompletoPokemon = jsonobj.toString()
                println(jsonCompletoPokemon)
            } else {
            }
        }

    }











    private var id = 0
    private var nombre=""
    private var nacionalidad=""
    private var listaPokemons = ""


}