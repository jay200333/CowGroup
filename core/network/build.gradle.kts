plugins {
    alias(libs.plugins.cowgroup.android.library)
    alias(libs.plugins.cowgroup.android.hilt)
    id("kotlinx-serialization")
}

android {
    namespace = "com.example.network"
}

dependencies {
    api(projects.core.common)
    api(projects.core.model)
    implementation(libs.kotlinx.serialization.json)
    api(libs.retrofit.core)
    implementation(libs.okhttp3.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.retrofit.kotlinx.serialization)
    api(libs.converter.gson)
    implementation(project(":core:datastore"))
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso.core)
}
