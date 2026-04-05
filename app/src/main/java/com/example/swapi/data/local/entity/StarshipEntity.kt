package com.example.swapi.data.local.entity
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "starships")
data class StarshipEntity(
    @PrimaryKey val id: String,
    val name: String,
    val model: String,
    val manufacturer: String,
    val costInCredits: String,
    val length: String,
    val maxAtmospheringSpeed: String,
    val crew: String,
    val passengers: String,
    val cargoCapacity: String,
    val consumables: String,
    val hyperdriveRating: String,
    val mglt: String,
    val starshipClass: String
)
