package dev.chamo.millieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.chamo.millieapp.core.designsystem.theme.MillieAppTheme
import dev.chamo.millieapp.navigation.MillieAppNavHost

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MillieAppTheme {
                MillieAppNavHost(
                    navController = rememberNavController(),
                )
            }
        }
    }
}