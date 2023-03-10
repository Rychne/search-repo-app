package com.rychne.searchrepo.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rychne.searchrepo.api.SearchRepoApi
import com.rychne.searchrepo.model.Repo
import com.rychne.searchrepo.repository.RepoPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class RepoViewModel @Inject constructor(
    private val searchRepoApi: SearchRepoApi
) : ViewModel() {
    var pagingDataFlow: MutableState<Flow<PagingData<Repo>>?> = mutableStateOf(null)

    fun searchRepos(query: String) {
        pagingDataFlow.value = Pager(PagingConfig(pageSize = 30, enablePlaceholders = true)) {
            RepoPagingSource(searchRepoApi, query)
        }.flow.cachedIn(viewModelScope)
    }
}