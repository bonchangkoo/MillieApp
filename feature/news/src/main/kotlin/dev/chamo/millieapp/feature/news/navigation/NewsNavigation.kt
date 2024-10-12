package dev.chamo.millieapp.feature.news.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.chamo.millieapp.feature.news.NewsRoute

const val NEWS_ROUTE = "news_route"

fun NavGraphBuilder.newsScreen(
    onNewsClick: (String) -> Unit,
    onBackClick: () -> Unit,
) {
    composable(route = NEWS_ROUTE) {
        NewsRoute(
            onNewsClick = onNewsClick,
            onBackClick = onBackClick
        )
    }
}