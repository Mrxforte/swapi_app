package com.example.swapi.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.swapi.data.local.dao.FilmDao
import com.example.swapi.data.local.dao.PersonDao
import com.example.swapi.data.local.dao.PlanetDao
import com.example.swapi.data.local.dao.SpeciesDao
import com.example.swapi.data.local.dao.StarshipDao
import com.example.swapi.data.local.dao.VehicleDao
import com.example.swapi.data.local.entity.FilmEntity
import com.example.swapi.data.local.entity.PersonEntity
import com.example.swapi.data.local.entity.PlanetEntity
import com.example.swapi.data.local.entity.SpeciesEntity
import com.example.swapi.data.local.entity.StarshipEntity
import com.example.swapi.data.local.entity.VehicleEntity

@Database(
    entities = [
        PersonEntity::class,
        FilmEntity::class,
        SpeciesEntity::class,
        PlanetEntity::class,
        StarshipEntity::class,
        VehicleEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
    abstract fun filmDao(): FilmDao
    abstract fun speciesDao(): SpeciesDao
    abstract fun planetDao(): PlanetDao
    abstract fun starshipDao(): StarshipDao
    abstract fun vehicleDao(): VehicleDao
}
