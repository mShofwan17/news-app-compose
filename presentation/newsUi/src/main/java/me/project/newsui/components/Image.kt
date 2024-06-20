package me.project.newsui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
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
import coil.compose.AsyncImage
import me.project.newsui.R

@Composable
fun MyRoundedImage(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    title: String
) {
    Box(
        modifier = modifier
            .padding(all = 8.dp)
            .clip(RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.BottomStart,
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize(),
            model = imageUrl,
            error = painterResource(id = R.drawable.no_image),
            contentDescription = stringResource(R.string.image),
            contentScale = ContentScale.Crop
        )

        Surface(
            modifier = Modifier
                .fillMaxHeight(0.4f)
                .fillMaxWidth(),
            color = Color.Black.copy(alpha = 0.5f),
            shape = RoundedCornerShape(
                bottomStart = 16.dp,
                bottomEnd = 16.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 8.dp)
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                )
            }
        }
    }
}

