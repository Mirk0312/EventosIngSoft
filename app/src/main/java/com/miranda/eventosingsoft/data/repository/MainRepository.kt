package com.miranda.eventosingsoft.data.repository

import com.miranda.eventosingsoft.data.local.ItemDao
import com.miranda.eventosingsoft.data.local.ItemEntity
import kotlinx.coroutines.flow.Flow

class MainRepository(private val itemDao: ItemDao) {

    // Obtiene todos los elementos de la tabla como un flujo reactivo
    val allItems: Flow<List<ItemEntity>> = itemDao.getAllItems()

    // Obtiene un flujo de un elemento específico por su ID
    fun getItemStream(id: Int): Flow<ItemEntity?> = itemDao.getItemById(id)

    // Inserta un nuevo elemento en la base de datos
    suspend fun insertItem(item: ItemEntity) = itemDao.insert(item)

    // Borra un elemento existente
    suspend fun deleteItem(item: ItemEntity) = itemDao.delete(item)

    // Actualiza los datos de un elemento
    suspend fun updateItem(item: ItemEntity) = itemDao.update(item)
}