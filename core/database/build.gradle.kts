plugins {
    alias(libs.plugins.millieapp.android.library)
    alias(libs.plugins.millieapp.android.hilt)
    alias(libs.plugins.millieapp.android.room)
}

android {
    namespace = "dev.chamo.millieapp.core.database"
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)
}
