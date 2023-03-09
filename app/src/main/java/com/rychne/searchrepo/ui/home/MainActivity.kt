package com.rychne.searchrepo.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.rychne.searchrepo.model.Repo
import com.rychne.searchrepo.ui.theme.SearchRepoAppTheme
import com.rychne.searchrepo.viewmodel.RepoListViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: RepoListViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchRepoAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    var query by remember { mutableStateOf("") }
                    val pager = remember {
                        viewModel.pager
                    }

                    val lazyPagingItems = pager.value?.flow?.collectAsLazyPagingItems()

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
                            PagingList(lazyPagingItems)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PagingList(lazyPagingItems: LazyPagingItems<Repo>) {
    LazyColumn(contentPadding = PaddingValues(8.dp)) {

        if (lazyPagingItems.loadState.refresh == LoadState.Loading) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Loading...")
                    CircularProgressIndicator()
                }
            }
        }

        itemsIndexed(lazyPagingItems) { _, item ->
            ListItem(
                headlineText = {
                    Text(
                        item?.name ?: "",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                supportingText = { Text(item?.description ?: "", fontSize = 18.sp) },
                trailingContent = { RepoListItemTrailingContent(repo = item) }
            )
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SearchRepoAppTheme {
//        PagingList()
    }
}