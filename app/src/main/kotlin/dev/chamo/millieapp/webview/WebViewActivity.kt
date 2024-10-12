package dev.chamo.millieapp.webview

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.viewinterop.AndroidView
import dev.chamo.millieapp.core.designsystem.component.MillieAppLoadingView

class WebViewActivity : ComponentActivity() {

    companion object {
        const val KEY_URL = "url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val url = intent.getStringExtra(KEY_URL) ?: ""

        setContent {
            WebViewScreen(url)
        }
    }
}

@Composable
fun WebViewScreen(url: String) {
    val isLoading = remember { mutableStateOf(true) }

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                webViewClient = object: WebViewClient() {
                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                        isLoading.value = true
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        isLoading.value = false
                    }
                }
                setLayerType(View.LAYER_TYPE_HARDWARE, null)
                with(settings) {
                    javaScriptEnabled = true
                    cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
                }
                loadUrl(url)
            }
        }
    )

    if (isLoading.value) {
        MillieAppLoadingView()
    }
}