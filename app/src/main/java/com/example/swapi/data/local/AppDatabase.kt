package com.example.swapi.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.swapi.data.local.dao.FilmDao
import com.example.swapi.data.local.dao.PersonDao
import com.example.swapi.data.local.dao.SpeciesDao
import com.example.swapi.data.local.entity.FilmEntity
import com.example.swapi.data.local.entity.PersonEntity
import com.example.swapi.data.local.entity.SpeciesEntity

@Database(
    entities = [PersonEntity::class, SpeciesEntity::class, FilmEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
    abstract fun speciesDao(): SpeciesDao
    abstract fun filmDao(): FilmDao
}
