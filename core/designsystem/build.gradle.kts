plugins {
    alias(libs.plugins.millieapp.android.library)
    alias(libs.plugins.millieapp.android.library.compose)
}

android {
    namespace = "dev.chamo.millieapp.core.designsystem"
}

dependencies {
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.ui.tooling.preview)

    implementation(libs.androidx.core.ktx)
}
