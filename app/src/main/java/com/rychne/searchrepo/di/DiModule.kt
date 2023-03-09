package com.rychne.searchrepo.di

import com.rychne.searchrepo.api.SearchRepoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiModule {

    @Provides
    @Singleton
    fun providesApi() : SearchRepoApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create()).baseUrl("https://api.github.com").build().create(SearchRepoApi::class.java)
    }
}