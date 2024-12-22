plugins {
    alias(libs.plugins.cowgroup.android.library)
    alias(libs.plugins.cowgroup.android.compose)
}

android {
    namespace = "com.example.cowgroup.core.designsystem"
}
dependencies {
    implementation(projects.core.model)
    implementation(libs.bundles.coil)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}
