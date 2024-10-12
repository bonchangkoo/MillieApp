plugins {
    alias(libs.plugins.millieapp.jvm.library)
    alias(libs.plugins.millieapp.android.hilt)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
}
