plugins {
    alias(libs.plugins.cowgroup.feature)
}

android {
    namespace = "com.example.login"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(libs.androidx.core.ktx)
    implementation(projects.core.navigation)
    implementation(projects.core.datastore)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}
