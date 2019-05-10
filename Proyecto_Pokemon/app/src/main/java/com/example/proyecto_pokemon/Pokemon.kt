package com.example.proyecto_pokemon

class Pokemon {


    var id: Number? = null
    var nombre: String? = null
    var nivel: Int? = null
    var tipo: String? = null
    var listaAtaques: List<String>? = null

    constructor(Id: Number, Nombre: String, Nivel: Int, Tipo: String, ListaAtaques: List<String>) : super() {
        this.id = Id
        this.nombre = Nombre
        this.nivel = Nivel
        this.tipo = Tipo
        this.listaAtaques = ListaAtaques
    }

}