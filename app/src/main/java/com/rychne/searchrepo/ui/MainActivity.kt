package com.rychne.searchrepo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rychne.searchrepo.ui.theme.SearchRepoAppTheme
import com.rychne.searchrepo.viewmodel.RepoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchRepoAppTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Search Repo App") },
                            navigationIcon = {
                                IconButton(onClick = {}) {
                                    Icon(Icons.Filled.ArrowBack, "backIcon")
                                }
                            },
                        )
                    }
                ) { contentPadding ->
                    val navController = rememberNavController()

                    val viewModel: RepoViewModel = hiltViewModel()


                    Box(modifier = Modifier.padding(contentPadding)) {
                        NavHost(navController = navController, startDestination = "repolist") {
                            composable("repolist") {
                                RepoListScreen(viewModel, openDetails = {
                                    navController.navigate(
                                        "repoDetail"
                                    )
                                })
                            }
                            composable("repodetail") { RepoDetailsScreen(viewModel) }
                        }
                    }

                }
            }
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
