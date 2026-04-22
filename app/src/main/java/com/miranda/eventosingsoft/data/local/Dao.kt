package com.miranda.eventosingsoft.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

//Data Access Object (DAO) para la entidad ItemEntity.
//Encapsula el acceso a la base de datos y define las operaciones CRUD.



// Establecemos la interfaz como un componente de acceso a datos para Room.
@Dao
interface EventoDao {
    @Query("SELECT * FROM eventos")
    fun getAllEvents(): Flow<List<EventoEntity>>

    //Si el id ya existe lo remplaza
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvento(evento: EventoEntity)

    @Delete
    suspend fun deleteEvento(evento: EventoEntity)
}