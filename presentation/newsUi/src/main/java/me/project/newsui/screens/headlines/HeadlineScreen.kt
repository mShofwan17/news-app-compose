package me.project.newsui.screens.headlines

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import me.project.domain.models.UiNews
import me.project.navigation.Screens
import me.project.newsui.R
import me.project.newsui.components.ListContent
import me.project.newsui.components.MyClipText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeadlineScreen(
    navHostController: NavHostController,
    viewModel: HeadlinesViewModel = hiltViewModel()
) {

    val headlineCategory = viewModel.headlineCategory.collectAsState()
    val selectedCategory = viewModel.selectedCategory.collectAsState()
    val headlineBanner = viewModel.headlineBanner.collectAsState()
    val items = viewModel.listPaging.collectAsLazyPagingItems()
    val error = viewModel.errorMsg.collectAsState()

    LaunchedEffect(key1 = selectedCategory.value) {
        viewModel.getHeadlineBanner(category = selectedCategory.value)
        viewModel.getHeadlineNews(category = selectedCategory.value)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Column {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = stringResource(R.string.news_app),
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.SemiBold
                )
                LazyRow {
                    items(count = headlineCategory.value.size) {
                        val item = headlineCategory.value[it]
                        val bodyColor =
                            if (item.isSelected) Color.Black else Color.LightGray
                        MyClipText(
                            modifier = Modifier
                                .padding(
                                    start = 8.dp,
                                    end = 8.dp,
                                    top = 2.dp,
                                    bottom = 16.dp
                                )
                                .clickable { viewModel.moveHeadlineCategory(item.category) },
                            color = bodyColor,
                            text = item.category,
                            textColor = if (item.isSelected) Color.White else Color.Black
                        )
                    }
                }
            }

        }
    ) { paddings ->
        PagingResult(
            items = items,
            onSuccess = {
                ListContent(
                    modifier = Modifier.padding(paddings),
                    banners = headlineBanner.value,
                    itemsPaging = items,
                    onItemClick = {
                        navigateToDetail(
                            navHostController,
                            it.title,
                            it.urlToImage
                        )
                    },
                    onBannerClick = {
                        navigateToDetail(
                            navHostController,
                            it.title,
                            it.urlToImage
                        )
                    }
                )
            },
            onError = { throwable, refresh ->
                viewModel.errorMessage(throwable)
                error.value?.let { msg ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddings),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ErrorPageHome(
                            message = msg,
                            onRefresh = {
                                viewModel.resetBaseState()
                                refresh.invoke()
                            }
                        )
                    }
                }
            },
            onLoading = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddings),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(50.dp),
                    )
                }
            }
        )
    }


}

private fun navigateToDetail(
    navHostController: NavHostController,
    title: String?,
    urlToImage: String?
) {
    navHostController.navigate(
        Screens.DetailNews
            .passArgument(title, urlToImage)
    )
}

@Composable
private fun PagingResult(
    items: LazyPagingItems<UiNews>,
    onSuccess: @Composable () -> Unit,
    onError: @Composable (error: Throwable, refresh: () -> Unit) -> Unit,
    onLoading: @Composable () -> Unit,
) {
    items.apply {
        when {
            loadState.append is LoadState.Error -> {
                onError((loadState.append as LoadState.Error).error) { refresh() }
            }

            loadState.refresh is LoadState.Loading -> {
                onLoading()
            }

            loadState.refresh is LoadState.Error -> {
                onError((loadState.refresh as LoadState.Error).error) { refresh() }
            }

            else -> onSuccess()

        }
    }
}

@Composable
fun ErrorPageHome(
    message: String,
    onRefresh: () -> Unit
) {
    Column(
        modifier = Modifier.padding(all = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier.size(150.dp),
            imageVector = Icons.Rounded.Clear,
            tint = Color.Red,
            contentDescription = stringResource(R.string.icon_info),
        )
        Spacer(modifier = Modifier.padding(top = 8.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = message,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.padding(top = 8.dp))

        Button(
            onClick = {
                onRefresh.invoke()
            }
        ) {
            Text(stringResource(R.string.refresh))
        }
    }
}