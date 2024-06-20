package me.project.newsui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import me.project.domain.models.UiHeadlineBanner
import me.project.domain.models.UiNews
import me.project.newsui.R

@Composable
fun ListContent(
    modifier: Modifier = Modifier,
    banners: List<UiHeadlineBanner>? = null,
    itemsList: List<UiNews>? = null,
    itemsPaging: LazyPagingItems<UiNews>? = null,
    onItemClick: (UiNews) -> Unit,
    onBannerClick: (UiHeadlineBanner) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .padding(8.dp)
    ) {
        banners?.let {
            item {
                LazyRow {
                    items(count = banners.size) {
                        val item = banners[it]
                        MyRoundedImage(
                            modifier = Modifier
                                .width(320.dp)
                                .height(200.dp)
                                .clickable { onBannerClick(item) },
                            title = item.title ?: "",
                            imageUrl = item.urlToImage
                        )
                    }
                }
            }
        }


        itemsList?.let {
            items(itemsList.size) {
                val item = itemsList[it]
                NewsItem(
                    item = item,
                    onClick = { news -> onItemClick.invoke(news) }
                )
            }
        }

        itemsPaging?.let {
            items(
                count = it.itemCount,
                key = it.itemKey { item ->
                    item.title
                }
            ) { position ->
                it[position]?.let { item ->
                    NewsItem(
                        item = item,
                        onClick = { news -> onItemClick.invoke(news) }
                    )
                }
            }
        }

    }
}

@Composable
private fun NewsItem(
    item: UiNews,
    onClick: (UiNews) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick.invoke(item) }
            .background(Color.LightGray)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                model = item.urlToImage,
                error = painterResource(id = R.drawable.no_image),
                contentDescription = stringResource(R.string.image),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                text = item.title,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                text = item.description ?: ""
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.publishedAt ?: "",
                style = TextStyle(color = Color.DarkGray),
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .align(Alignment.End)
            )
            Spacer(modifier = Modifier.height(10.dp))
        }

    }
}