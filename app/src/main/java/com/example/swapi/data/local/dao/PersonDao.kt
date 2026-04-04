package com.example.swapi.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.swapi.data.local.entity.PersonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {
    @Query("SELECT * FROM people ORDER BY name ASC")
    fun observePeople(): Flow<List<PersonEntity>>

    @Query("SELECT * FROM people WHERE id = :personId LIMIT 1")
    fun observePersonById(personId: String): Flow<PersonEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(people: List<PersonEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(person: PersonEntity)

    @Query("DELETE FROM people")
    suspend fun clearAll()
}
