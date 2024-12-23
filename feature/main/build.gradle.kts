plugins {
    alias(libs.plugins.cowgroup.feature)
}

android {
    namespace = "com.example.main"
}

dependencies {
    implementation(libs.kotlinx.immutable)
    implementation(projects.feature.login)
    implementation(projects.core.domain)
    implementation(projects.core.data)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}
