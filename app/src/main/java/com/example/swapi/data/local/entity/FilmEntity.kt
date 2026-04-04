package com.example.swapi.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "films")
data class FilmEntity(
    @PrimaryKey val id: String,
    val title: String,
    val openingCrawl: String
)
