plugins {
    alias(libs.plugins.millieapp.android.library)
    alias(libs.plugins.millieapp.android.hilt)
    alias(libs.plugins.kotlin.serialization)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    buildFeatures {
        buildConfig = true
    }
    namespace = "dev.chamo.mycletest.core.network"
}

secrets {
    defaultPropertiesFileName = "secrets.defaults.properties"
}

dependencies {
    api(project(":core:model"))

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
}