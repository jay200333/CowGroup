plugins {
    alias(libs.plugins.cowgroup.android.library)
    alias(libs.plugins.cowgroup.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.data"
}

dependencies {
    implementation(projects.core.common)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}