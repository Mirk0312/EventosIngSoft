package com.miranda.eventosingsoft.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [EventoEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun eventoDao(): EventoDao
    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // Si Instance no es nulo, lo regresa y si es nulo, crea la base de datos
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "eventos_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it } //
            }
        }
    }
}