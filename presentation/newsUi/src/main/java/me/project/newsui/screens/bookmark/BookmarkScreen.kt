package me.project.newsui.screens.bookmark

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import me.project.navigation.Screens
import me.project.newsui.components.ListContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkScreen(
    navHostController: NavHostController,
    viewModel: BookmarkViewModel = hiltViewModel()
) {
    val items = viewModel.bookmark.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Bookmark News") },
            )
        }
    ) { paddings ->

        LaunchedEffect(key1 = Unit) {
            viewModel.getBookmarks()
        }

        ListContent(
            modifier = Modifier.padding(paddings),
            itemsList = items.value,
            onItemClick = {
                navHostController.navigate(
                    Screens.DetailNews.passArgument(
                        it.title,
                        it.urlToImage
                    )
                )
            }
        )
    }
}