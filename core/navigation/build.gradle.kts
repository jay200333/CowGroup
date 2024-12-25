plugins {
    alias(libs.plugins.cowgroup.android.library)
    alias(libs.plugins.cowgroup.android.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.navigation"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(projects.core.model)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso.core)
}
