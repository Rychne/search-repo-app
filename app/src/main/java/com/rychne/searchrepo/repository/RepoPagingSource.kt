package com.rychne.searchrepo.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rychne.searchrepo.api.SearchRepoApi
import com.rychne.searchrepo.model.Repo
import retrofit2.HttpException
import java.io.IOException


class RepoPagingSource(private val dataSource: SearchRepoApi, private val query: String) :
    PagingSource<Int, Repo>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = dataSource.getRepositories(query, nextPageNumber).body()?.items ?: emptyList()
            LoadResult.Page(
                data = response,
                prevKey = nextPageNumber - 1,
                nextKey = nextPageNumber + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}