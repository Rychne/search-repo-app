package com.rychne.searchrepo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.rychne.searchrepo.model.Repo
import com.rychne.searchrepo.viewmodel.RepoViewModel
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoListScreen(viewModel: RepoViewModel, openDetails: () -> Unit) {

    var query by remember { mutableStateOf("") }
    val pagingDataFlow = remember {
        viewModel.pagingDataFlow
    }

    val lazyPagingItems = pagingDataFlow.value?.collectAsLazyPagingItems()

    Column(modifier = Modifier.padding(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            SearchBar(
                query = query,
                onQueryChange = { query = it },
                onSearch = { viewModel.searchRepos(query) },
                active = false,
                onActiveChange = {},
                placeholder = { Text("Enter some text") }
            ) {}
        }

        if (lazyPagingItems != null) {
            RepoList(lazyPagingItems) { repo: Repo -> viewModel.selectRepo(repo); openDetails() }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoList(lazyPagingItems: LazyPagingItems<Repo>, onClicked: (Repo) -> Unit) {


    LazyColumn(contentPadding = PaddingValues(8.dp)) {

        if (lazyPagingItems.loadState.refresh == LoadState.Loading) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "Loading... ")
                    CircularProgressIndicator()
                }
            }
        }

        itemsIndexed(lazyPagingItems) { _, item ->
            item?.let {
                Card(onClick = { onClicked(it) }) {
                    ListItem(
                        headlineText = {
                            Text(
                                it.name,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        supportingText = { Text(it.description ?: "", fontSize = 18.sp) },
                        trailingContent = { RepoListItemTrailingContent(repo = it) },
                    )
                }
            }

            Divider()
        }

        if (lazyPagingItems.loadState.append == LoadState.Loading) {
            item {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
fun RepoListItemTrailingContent(repo: Repo?) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(SimpleDateFormat.getDateTimeInstance().format(repo?.updatedAt ?: "").toString())

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = repo?.stars.toString())
            Icon(Icons.Rounded.Star, contentDescription = "")
        }
    }
}