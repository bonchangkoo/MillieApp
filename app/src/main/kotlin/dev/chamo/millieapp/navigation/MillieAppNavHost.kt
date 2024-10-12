package dev.chamo.millieapp.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import dev.chamo.millieapp.feature.news.navigation.NEWS_ROUTE
import dev.chamo.millieapp.feature.news.navigation.newsScreen
import dev.chamo.millieapp.webview.WebViewActivity
import dev.chamo.millieapp.webview.WebViewActivity.Companion.KEY_URL

@Composable
fun MillieAppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = NEWS_ROUTE,
        modifier = modifier
    ) {
        newsScreen(
            onNewsClick = { url ->
                context.openWebView(url)
            },
            onBackClick = {
                if (context is Activity) {
                    context.finish()
                }
            },
        )
    }
}

private fun Context.openWebView(url: String) {
    if (url.isBlank()) {
        return
    }
    val intent = Intent(this, WebViewActivity::class.java).apply {
        putExtra(KEY_URL, url)
    }
    this.startActivity(intent)
}