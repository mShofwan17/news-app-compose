package me.project.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import me.project.navigation.Screens
import me.project.navigation.SetupNavGraph
import me.project.newsapp.ui.theme.NewsAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                // A surface container using the 'background' color from the theme
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    val navController = rememberNavController()
                    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
                    val navBackStackEntry by navController.currentBackStackEntryAsState()

                    when (navBackStackEntry?.destination?.route) {
                        Screens.Headline.route -> bottomBarState.value = true
                        Screens.Bookmark.route -> bottomBarState.value = true
                        else -> bottomBarState.value = false
                    }

                    Scaffold(
                        bottomBar = {
                            Surface(
                                shadowElevation = 9.dp
                            ) {
                                if (bottomBarState.value) BottomNavigationApp(navController = navController)
                            }
                        }
                    ) { innerPadding ->
                        SetupNavGraph(navController, Modifier.padding(innerPadding))
                    }

                }
            }
        }
    }
}

@Composable
fun BottomNavigationApp(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    AnimatedVisibility(
        visible = true,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        NavigationBar(
            modifier = modifier,
            containerColor = Color.White,
        ) {

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            val items = listOf(Screens.Headline, Screens.Bookmark)
            items.forEach { screen ->
                NavigationBarItem(
                    label = {
                        Text(text = screen.label)
                    },
                    icon = {
                        screen.icon?.let {
                            Icon(
                                imageVector = it,
                                contentDescription = stringResource(R.string.bottom_icon)
                            )
                        }

                    },
                    selected = currentRoute == screen.route,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.LightGray,
                        unselectedTextColor = Color.Gray,
                        selectedTextColor = Color.Black,
                        selectedIconColor = Color.Black,
                        unselectedIconColor = Color.Gray,
                    ),
                )
            }
        }
    }
}
