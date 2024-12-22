plugins {
    alias(libs.plugins.cowgroup.android.compose)
    alias(libs.plugins.cowgroup.android.library)
}

android {
    namespace = "com.example.ui"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.designsystem)
    implementation(libs.bundles.coil)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}