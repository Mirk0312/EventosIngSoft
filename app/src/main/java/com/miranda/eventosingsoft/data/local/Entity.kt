package com.miranda.eventosingsoft.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "eventos")
data class EventoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titulo: String,
    val descripcion: String,
    val categoria: String,
    val ubicacion: String,
    val fecha: String,
    val isFavorite: Boolean = false
)