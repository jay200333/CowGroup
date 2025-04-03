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
    implementation(libs.androidx.paging.compose)
    api(projects.core.network)
    api(projects.core.database)
    implementation(projects.core.model)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}
