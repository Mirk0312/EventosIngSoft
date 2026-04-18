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
interface ItemDao {

    // Retorna un Flow para permitir una UI reactiva en Jetpack Compose.
    @Query("SELECT * FROM items")
    fun getAllItems(): Flow<List<ItemEntity>>

    @Query("SELECT * FROM items WHERE id = :id")
    fun getItemById(id: Int): Flow<ItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE) // Si el ID ya existe, lo reemplaza.
    suspend fun insert(item: ItemEntity)

    @Update
    suspend fun update(item: ItemEntity)

    @Delete
    suspend fun delete(item: ItemEntity)
}