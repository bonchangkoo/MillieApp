package dev.chamo.millieapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import dev.chamo.millieapp.feature.news.navigation.NEWS_ROUTE
import dev.chamo.millieapp.feature.news.navigation.newsScreen

@Composable
fun MillieAppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NEWS_ROUTE,
        modifier = modifier
    ) {
        newsScreen()
    }
}