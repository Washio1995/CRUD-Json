package com.example.proyecto_pokemo


class Entrenador {
    var id: Number? = null
    var nombre: String? = null
    var nacionalidad: String? = null
    var listaPokemon: String? = null

    constructor(Id: Number, Nombre: String, Nacionalidad: String, ListaPokemon: String
    ) : super() {
        this.id = Id
        this.nombre = Nombre
        this.nacionalidad = Nacionalidad
        this.listaPokemon = ListaPokemon
    }

}