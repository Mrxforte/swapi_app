package com.example.swapi.di

import android.content.Context
import androidx.room.Room
import com.example.swapi.data.local.AppDatabase
import com.example.swapi.data.local.dao.FilmDao
import com.example.swapi.data.local.dao.PersonDao
import com.example.swapi.data.local.dao.PlanetDao
import com.example.swapi.data.local.dao.SpeciesDao
import com.example.swapi.data.local.dao.StarshipDao
import com.example.swapi.data.local.dao.VehicleDao
import com.example.swapi.data.remote.SwapiApi
import com.example.swapi.data.repository.FilmsRepositoryImpl
import com.example.swapi.data.repository.PeopleRepositoryImpl
import com.example.swapi.data.repository.PlanetsRepositoryImpl
import com.example.swapi.data.repository.SpeciesRepositoryImpl
import com.example.swapi.data.repository.StarshipsRepositoryImpl
import com.example.swapi.data.repository.VehiclesRepositoryImpl
import com.example.swapi.domain.repository.FilmsRepository
import com.example.swapi.domain.repository.PeopleRepository
import com.example.swapi.domain.repository.PlanetsRepository
import com.example.swapi.domain.repository.SpeciesRepository
import com.example.swapi.domain.repository.StarshipsRepository
import com.example.swapi.domain.repository.VehiclesRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
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
    fun provideMoshi(): Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideSwapiApi(client: OkHttpClient, moshi: Moshi): SwapiApi =
        Retrofit.Builder()
            .baseUrl("https://swapi.dev/api/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(SwapiApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "swapi.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides fun providePersonDao(db: AppDatabase): PersonDao = db.personDao()
    @Provides fun provideFilmDao(db: AppDatabase): FilmDao = db.filmDao()
    @Provides fun provideSpeciesDao(db: AppDatabase): SpeciesDao = db.speciesDao()
    @Provides fun providePlanetDao(db: AppDatabase): PlanetDao = db.planetDao()
    @Provides fun provideStarshipDao(db: AppDatabase): StarshipDao = db.starshipDao()
    @Provides fun provideVehicleDao(db: AppDatabase): VehicleDao = db.vehicleDao()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds abstract fun bindPeopleRepository(impl: PeopleRepositoryImpl): PeopleRepository
    @Binds abstract fun bindFilmsRepository(impl: FilmsRepositoryImpl): FilmsRepository
    @Binds abstract fun bindPlanetsRepository(impl: PlanetsRepositoryImpl): PlanetsRepository
    @Binds abstract fun bindSpeciesRepository(impl: SpeciesRepositoryImpl): SpeciesRepository
    @Binds abstract fun bindStarshipsRepository(impl: StarshipsRepositoryImpl): StarshipsRepository
    @Binds abstract fun bindVehiclesRepository(impl: VehiclesRepositoryImpl): VehiclesRepository
}
