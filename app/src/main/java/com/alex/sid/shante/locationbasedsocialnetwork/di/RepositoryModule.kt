package com.alex.sid.shante.locationbasedsocialnetwork.di

import com.alex.sid.shante.locationbasedsocialnetwork.data.MapRepositoryImpl
import com.alex.sid.shante.locationbasedsocialnetwork.data.NominatimSearchApi
import com.alex.sid.shante.locationbasedsocialnetwork.domain.repositories.MapRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideMapReository(
        nominatimSearchApi: NominatimSearchApi
    ): MapRepository {
        return MapRepositoryImpl(nominatimSearchApi)
    }
}