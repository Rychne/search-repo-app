package com.rychne.searchrepo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.rychne.searchrepo.viewmodel.RepoViewModel
import java.text.SimpleDateFormat

@Composable
fun RepoDetailsScreen(viewModel: RepoViewModel) {


    val repo = remember {
        viewModel.clickedRepo
    }

    repo.value?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = it.owner.avatarUrl,
                    contentDescription = it.owner.login,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(100.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {

                    Text(text = it.owner.login, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = it.owner.url)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = it.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = it.stars.toString())
                Spacer(modifier = Modifier.width(2.dp))
                Icon(Icons.Rounded.Star, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = it.forksCount.toString())
                Spacer(modifier = Modifier.width(2.dp))
                Text(text = "forks")

            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = it.link ?: "", fontSize = 12.sp)
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Created at ${SimpleDateFormat.getDateTimeInstance().format(it.createdAt)}",
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Latest update at ${
                    SimpleDateFormat.getDateTimeInstance().format(it.updatedAt)
                }", fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = it.description ?: "")
        }
    }
}