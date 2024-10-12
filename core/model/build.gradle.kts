plugins {
    alias(libs.plugins.millieapp.jvm.library)
    id("kotlinx-serialization")
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
    api(libs.kotlinx.datetime)
}
