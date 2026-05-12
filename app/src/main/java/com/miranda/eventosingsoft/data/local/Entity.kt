package com.miranda.eventosingsoft.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "eventos")
data class EventoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // El 0 le dice al Room que genere el ID en auto
    val titulo: String,
    val descripcion: String,
    val fecha: String,
    val ubicacion: String,
    val esFavorito: Boolean = false,
    val imagenUri: String? = null
)