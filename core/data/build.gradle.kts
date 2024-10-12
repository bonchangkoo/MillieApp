plugins {
    alias(libs.plugins.millieapp.android.library)
    alias(libs.plugins.millieapp.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "dev.chamo.millieapp.core.data"
}


dependencies {
    api(project(":core:common"))
    api(project(":core:network"))
}
