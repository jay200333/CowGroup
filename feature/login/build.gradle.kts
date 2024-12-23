plugins {
    alias(libs.plugins.cowgroup.feature)
}

android {
    namespace = "com.example.login"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.data)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}
