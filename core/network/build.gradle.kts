plugins {
    alias(libs.plugins.cowgroup.android.library)
    alias(libs.plugins.cowgroup.android.hilt)
    id("kotlinx-serialization")
}

android {
    namespace = "com.example.network"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.model)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.core)
    implementation(libs.okhttp3.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.retrofit.kotlinx.serialization)
    implementation(libs.converter.gson)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso.core)
}
