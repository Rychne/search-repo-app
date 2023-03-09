package com.rychne.searchrepo.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.rychne.searchrepo.api.SearchRepoApi
import com.rychne.searchrepo.model.Repo
import com.rychne.searchrepo.repository.RepoPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RepoListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle, private val searchRepoApi: SearchRepoApi
) : ViewModel() {
    var pager: MutableState<Pager<Int, Repo>?> = mutableStateOf(null)

    fun searchRepos(query: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                pager.value = Pager(PagingConfig(pageSize = 30, enablePlaceholders = true)) {
                    RepoPagingSource(searchRepoApi, query)
                }
            }
        }
    }
}