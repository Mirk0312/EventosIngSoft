package com.miranda.eventosingsoft

import android.app.Application
import com.miranda.eventosingsoft.data.local.AppDatabase
import com.miranda.eventosingsoft.data.repository.MainRepository

class EventosApp : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { MainRepository(database.eventoDao()) }
}