plugins {
    alias(libs.plugins.millieapp.android.library)
    alias(libs.plugins.millieapp.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "dev.chamo.millieapp.core.data"
}


dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:network"))
    implementation(project(":core:database"))
}
