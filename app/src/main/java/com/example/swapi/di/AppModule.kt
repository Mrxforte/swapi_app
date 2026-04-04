package com.example.swapi.di

import android.content.Context
import androidx.room.Room
import com.example.swapi.data.local.AppDatabase
import com.example.swapi.data.local.dao.FilmDao
import com.example.swapi.data.local.dao.PersonDao
import com.example.swapi.data.local.dao.SpeciesDao
import com.example.swapi.data.remote.SwapiApi
import com.example.swapi.data.repository.PeopleRepositoryImpl
import com.example.swapi.domain.repository.PeopleRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkDatabaseModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun provideSwapiApi(client: OkHttpClient): SwapiApi {
        return Retrofit.Builder()
            .baseUrl("https://swapi.dev/api/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(SwapiApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "swapi.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providePersonDao(database: AppDatabase): PersonDao = database.personDao()

    @Provides
    fun provideSpeciesDao(database: AppDatabase): SpeciesDao = database.speciesDao()

    @Provides
    fun provideFilmDao(database: AppDatabase): FilmDao = database.filmDao()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindPeopleRepository(impl: PeopleRepositoryImpl): PeopleRepository
}
