package com.rychne.searchrepo.di

import com.rychne.searchrepo.api.SearchRepoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object DiModule {

    @Provides
    fun providesApi() : SearchRepoApi {
        return Retrofit.Builder().baseUrl("https://api.github.com").build().create(SearchRepoApi::class.java)
    }
}