plugins {
    alias(libs.plugins.cowgroup.feature)
}

android {
    namespace = "com.example.home"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}