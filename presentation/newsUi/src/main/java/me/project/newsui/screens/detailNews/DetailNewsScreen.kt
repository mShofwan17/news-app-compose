package me.project.newsui.screens.detailNews

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import me.project.newsui.R
import me.project.newsui.components.MyButton

@Composable
fun DetailNewsScreen(
    navHostController: NavHostController,
    viewmodel: DetailNewsViewModel = hiltViewModel()
) {

    val content = viewmodel.newsDetail.collectAsState()
    val stateBookmark = viewmodel.add.collectAsState()
    val stateUnbookmark = viewmodel.delete.collectAsState()
    val isContentExist = viewmodel.isBookmarkExist.collectAsState()
    val errorMsg = viewmodel.errorMsg.collectAsState()
    val imageUrl = viewmodel.imageUrl.collectAsState()
    val loading = viewmodel.loading.collectAsState()
    var contentLoading by remember {
        mutableStateOf(false)
    }
    stateBookmark.value?.let {
        contentLoading = false
        viewmodel.resetState()
    }
    stateUnbookmark.value?.let {
        contentLoading = false
        viewmodel.resetState()
    }

    errorMsg.value?.let {
        navHostController.popBackStack()
        Toast.makeText(LocalContext.current, it, Toast.LENGTH_LONG).show()
        viewmodel.resetBaseState()
    }

    if (loading.value) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(50.dp),
            )
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(
                        start = 16.dp, end = 16.dp,
                        bottom = 16.dp, top = 6.dp
                    ),
            ) {
                content.value?.let {
                    val modifier = Modifier.align(Alignment.BottomCenter)
                    var title = stringResource(R.string.add_to_bookmark)
                    var backgroundColor = Color.Black
                    var icon = Icons.Default.Favorite

                    if (isContentExist.value == true) {
                        title = stringResource(R.string.delete_from_bookmark)
                        backgroundColor = Color.Red
                        icon = Icons.Default.Delete
                    }

                    MyButton(
                        modifier = modifier,
                        title = title,
                        backgroundColor = backgroundColor,
                        icon = icon,
                        contentLoading = contentLoading,
                        onClick = {
                            contentLoading = true
                            val item = it.copy(urlToImage = imageUrl.value)
                            if (isContentExist.value == true) viewmodel.deleteBookmark(item)
                            else viewmodel.addBookmark(item)
                        },
                        isClickAble = !contentLoading
                    )
                }
            }
        }
    ) { paddingValues ->

        content.value?.let { content ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(Color.White)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(
                            RoundedCornerShape(
                                bottomStart = 4.dp,
                                bottomEnd = 4.dp
                            )
                        )
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.BottomStart,
                    ) {

                        AsyncImage(
                            modifier = Modifier
                                .fillMaxSize(),
                            model = imageUrl.value,
                            error = painterResource(id = R.drawable.no_image),
                            contentDescription = stringResource(id = R.string.image),
                            contentScale = ContentScale.Crop
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.3f)),
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    modifier = Modifier.padding(end = 6.dp),
                                    imageVector = Icons.Default.DateRange,
                                    tint = Color.White,
                                    contentDescription = stringResource(
                                        id = R.string.icon_button
                                    )

                                )
                                Text(
                                    modifier = Modifier.padding(start = 6.dp, end = 16.dp),
                                    text = content.publishedAt ?: "",
                                    color = Color.White
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    modifier = Modifier.padding(end = 6.dp),
                                    imageVector = Icons.Default.Person,
                                    tint = Color.White,
                                    contentDescription = stringResource(
                                        id = R.string.icon_button
                                    )

                                )
                                Text(
                                    modifier = Modifier.padding(start = 6.dp, end = 10.dp),
                                    text = content.author ?: "",
                                    color = Color.White
                                )
                            }
                        }


                    }

                    Icon(
                        modifier = Modifier
                            .size(50.dp)
                            .padding(top = 16.dp)
                            .clickable { navHostController.popBackStack() },
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = stringResource(R.string.ic_back),
                        tint = Color.White
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    VerticalDivider(
                        modifier = Modifier
                            .height(50.dp),
                        thickness = 8.dp,
                        color = Color.Black
                    )
                    Text(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                        text = content.title ?: "",
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    )
                }

                Text(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    text = content.description ?: "",
                    color = Color.DarkGray,
                    textAlign = TextAlign.Justify
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    text = content.content ?: "",
                    color = Color.DarkGray,
                    textAlign = TextAlign.Justify
                )
            }
        }
    }


}

