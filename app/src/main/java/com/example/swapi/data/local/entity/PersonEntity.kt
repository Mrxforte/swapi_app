package com.example.swapi.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "people")
data class PersonEntity(
    @PrimaryKey val id: String,
    val name: String,
    val gender: String,
    val birthYear: String,
    val height: String,
    val mass: String,
    val hairColor: String,
    val skinColor: String,
    val eyeColor: String,
    val speciesIdsCsv: String,
    val filmIdsCsv: String
)
