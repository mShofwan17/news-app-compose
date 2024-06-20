package me.project.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import me.project.newsui.screens.bookmark.BookmarkScreen
import me.project.newsui.screens.detailNews.DetailNewsScreen
import me.project.newsui.screens.headlines.HeadlineScreen

@Composable
fun SetupNavGraph(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navHostController,
        startDestination = Screens.Headline.route,
        modifier = modifier
    ) {
        composable(route = Screens.Headline.route) {
            HeadlineScreen(navHostController = navHostController)
        }
        composable(route = Screens.Bookmark.route) {
            BookmarkScreen(navHostController = navHostController)
        }
        composable(
            route = Screens.DetailNews.route,
            arguments = listOf(
                navArgument(name = NavConstant.DETAIL_TITLE_ARG) { type = NavType.StringType },
                navArgument(name = NavConstant.DETAIL_IMAGE_ARG) {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {
            DetailNewsScreen(navHostController = navHostController)
        }
    }
}