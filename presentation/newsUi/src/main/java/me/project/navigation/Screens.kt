package me.project.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

sealed class Screens(val route: String, val label: String, val icon: ImageVector? = null) {
    data object Headline : Screens(NavConstant.HEADLINE, "Headlines News", Icons.Default.Home)
    data object Bookmark : Screens(NavConstant.BOOKMARK, "Bookmark", Icons.Default.FavoriteBorder)
    data object DetailNews :
        Screens(route = "${NavConstant.DETAIL_NEWS}/{title}/{image}", label = "Detail News") {
        fun passArgument(title: String?, image: String?): String {
            val encodedUrl = if (image!=null) URLEncoder.encode(image, StandardCharsets.UTF_8.toString())
            else null
            return "${NavConstant.DETAIL_NEWS}/$title/$encodedUrl"
        }
    }
}