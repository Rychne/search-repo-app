package com.rychne.searchrepo.api

import com.rychne.searchrepo.model.SearchRepoResultDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface SearchRepoApi {
    @GET("/search/repositories")
    suspend fun getRepositories(@Query("q") q: String, @Query("page") page: Int) : Response<SearchRepoResultDto>
}