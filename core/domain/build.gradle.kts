plugins {
    alias(libs.plugins.cowgroup.android.library)
}

android {
    namespace = "com.example.domain"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.model)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}
