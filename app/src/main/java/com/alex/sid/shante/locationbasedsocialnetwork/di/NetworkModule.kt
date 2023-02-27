package com.alex.sid.shante.locationbasedsocialnetwork.di

import com.alex.sid.shante.locationbasedsocialnetwork.data.NominatimSearchApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideBaseUrl() = "https://nominatim.openstreetmap.org/"

    @Provides
    @Singleton
    fun provideRetrofitBuilder(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideGitApi(retrofit: Retrofit): NominatimSearchApi {
        return retrofit.create(NominatimSearchApi::class.java)
    }
}