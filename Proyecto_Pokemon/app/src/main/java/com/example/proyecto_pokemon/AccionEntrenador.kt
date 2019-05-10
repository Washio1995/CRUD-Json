package com.example.proyecto_pokemon

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import com.example.proyecto_pokemo.Entrenador
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_accion_entrenador.*
import org.json.JSONArray
import java.io.BufferedReader
import java.io.File
import java.io.InputStream

class AccionEntrenador : AppCompatActivity() {


    private var jsonInicial:String? = null
    var arrListaEntrenadoresFiltrados = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accion_entrenador)

        establecerValoresEnPantalla()

        botonEliminarEntrenador.setOnClickListener {
            filtrarJsonArray()
            eliminarRegistro()
            mostrarMensaje("Entrenador Eliminado")
        }

        botonActualizarEntrenador.setOnClickListener {
            filtrarJsonArray()
            eliminarRegistro()
            tomarInputs()
            actualizarRegistroJson(id,nombre, nacionalidad, listaPokemon)
            mostrarMensaje("Registro de Entrenador Actualizado")
        }

    }


    private fun actualizarRegistroJson(Id: Int, Nombre: String, Nacionalidad: String,ListaPokemon: String) {
        leerJson()
        var post = Entrenador(Id, Nombre, Nacionalidad, ListaPokemon)
        var gson = Gson()
        var jsonString: String = gson.toJson(post)
        var jsonString2: String = jsonInicial.toString()
        var jsonFinal = jsonString2 + ',' + jsonString.toString()
        val file = File("/data/data/com.example.proyecto_pokemon/files/Entrenadores.json")
        file.writeText(jsonFinal)
        guardarFormatoJson(jsonFinal)

    }

  fun eliminarRegistro(){

      var jsonEliminado = ""

      for (i in 0..arrListaEntrenadoresFiltrados.size-2) {
          jsonEliminado = jsonEliminado + arrListaEntrenadoresFiltrados.get(i) + ","
      }
      jsonEliminado = jsonEliminado + arrListaEntrenadoresFiltrados.get(arrListaEntrenadoresFiltrados.size-1)

      val file = File("/data/data/com.example.proyecto_pokemon/files/Entrenadores.json")
      file.writeText(jsonEliminado)
      guardarFormatoJson(jsonEliminado)
  }


    private fun guardarFormatoJson(json: String){

        val jsonFormatoFinal= "[" + json.toString() + "]"
        val file = File("/data/data/com.example.proyecto_pokemon/files/EntrenadoresJSON.json")
        file.writeText(jsonFormatoFinal)

    }

    private fun filtrarJsonArray() {
        var json: String? = null
            val inputStream: InputStream = File("/data/data/com.example.proyecto_pokemon/files/Entrenadores.json").inputStream()
            json = inputStream.bufferedReader().use { it.readText() }
            val jsonCompleto: String = "[" + json.toString() + "]"
            var jsonarr = JSONArray(jsonCompleto)

            for (i in 0..jsonarr.length()-1) {
                if( i == Pantalla_Entrenador.index.indexSeleccionado) {
                    //var jsonobj = jsonarr.getJSONObject(i)
                    //arrListaEntrenador.add(jsonobj.getString("nombre"))
               } else {
                    arrListaEntrenadoresFiltrados.add(jsonarr.getJSONObject(i).toString())
                    //println(jsonarr.getJSONObject(i).toString())
                    //val temporal = jsonarr.getJSONObject(i).toString()
                }
            }

    }





    private fun establecerValoresEnPantalla() {
        var json: String? = null
        val inputStream: InputStream = File("/data/data/com.example.proyecto_pokemon/files/Entrenadores.json").inputStream()
        json = inputStream.bufferedReader().use { it.readText() }
        val jsonCompleto: String = "[" + json.toString() + "]"
        var jsonarr = JSONArray(jsonCompleto)

        for (i in 0..jsonarr.length()-1) {
            if( i == Pantalla_Entrenador.index.indexSeleccionado) {
                var jsonobj = jsonarr.getJSONObject(i)
                inputId.setText(jsonobj.getString("id"))
                inputNombre.setText(jsonobj.getString("nombre"))
                inputNacionalidad.setText(jsonobj.getString("nacionalidad"))
            } else {
            }
        }
    }


    private fun mostrarMensaje(mensaje: String) {
        val dialogBuilder = android.app.AlertDialog.Builder(this)
        dialogBuilder.setMessage(mensaje)
            .setCancelable(false)
            .setPositiveButton("OK", DialogInterface.OnClickListener() { dialog, id ->
                finish()
            })

        val alert = dialogBuilder.create()
        alert.show()

    }

    private fun leerJson() {
        var gson = Gson()
        val bufferedReader: BufferedReader =
            File("/data/data/com.example.proyecto_pokemon/files/Entrenadores.json").bufferedReader()
        val inputString = bufferedReader.use { it.readText() }
        jsonInicial = inputString
    }

    private fun tomarInputs() {
        val et_idInput = findViewById<TextInputEditText>(R.id.inputId)
        val et_nombreInput = findViewById<TextInputEditText>(R.id.inputNombre)
        val et_nacionalidadInput = findViewById<TextInputEditText>(R.id.inputNacionalidad)
        id = et_idInput.text.toString().toInt()
        nombre = et_nombreInput.text.toString()
        nacionalidad = et_nacionalidadInput.text.toString()

    }











    private var id = 0
    private var nombre=""
    private var nacionalidad=""
    private var listaPokemon= ""


}




