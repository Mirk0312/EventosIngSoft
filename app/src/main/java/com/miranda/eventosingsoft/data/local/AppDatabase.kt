package com.miranda.eventosingsoft.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


//Se define la configuración central de Room
@Database(entities = [ItemEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao

    companion object {

        //Asegura la visibilidad de cambios entre hilos
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            //Si la instancia es nula, se crea una nueva de forma sincronizada
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "item_database" //Nombre del archivo físico SQLite
                )
                    .fallbackToDestructiveMigration() //Permite recrear la BD si cambia la versión
                    .build()
                    .also { Instance = it } //Se almacena en memoria para futuras llamadas
            }
        }
    }
}