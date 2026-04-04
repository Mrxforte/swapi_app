package com.example.swapi.data.mapper

import com.example.swapi.data.local.entity.FilmEntity
import com.example.swapi.data.local.entity.PersonEntity
import com.example.swapi.data.local.entity.SpeciesEntity
import com.example.swapi.data.remote.dto.FilmDto
import com.example.swapi.data.remote.dto.PersonDto
import com.example.swapi.data.remote.dto.SpeciesDto
import com.example.swapi.domain.model.FilmSummary
import com.example.swapi.domain.model.Person

fun parseIdFromUrl(url: String): String = url.trimEnd('/').substringAfterLast('/')

fun String.csvToIds(): List<String> = split(',').map { it.trim() }.filter { it.isNotBlank() }

fun PersonDto.toEntity(): PersonEntity {
    val parsedId = parseIdFromUrl(url)
    return PersonEntity(
        id = parsedId,
        name = name,
        gender = gender,
        birthYear = birthYear,
        height = height,
        mass = mass,
        hairColor = hairColor,
        skinColor = skinColor,
        eyeColor = eyeColor,
        speciesIdsCsv = species.map(::parseIdFromUrl).joinToString(","),
        filmIdsCsv = films.map(::parseIdFromUrl).joinToString(",")
    )
}

fun PersonEntity.toDomain(): Person = Person(
    id = id,
    name = name,
    gender = gender,
    birthYear = birthYear,
    height = height,
    mass = mass,
    hairColor = hairColor,
    skinColor = skinColor,
    eyeColor = eyeColor
)

fun SpeciesDto.toEntity(): SpeciesEntity = SpeciesEntity(
    id = parseIdFromUrl(url),
    name = name
)

fun FilmDto.toEntity(): FilmEntity = FilmEntity(
    id = parseIdFromUrl(url),
    title = title,
    openingCrawl = openingCrawl.replace("\n", " ").trim()
)

fun FilmEntity.toDomain(): FilmSummary = FilmSummary(
    id = id,
    title = title,
    openingCrawl = openingCrawl
)
