plugins {
    alias(libs.plugins.cowgroup.android.library)
    alias(libs.plugins.cowgroup.android.hilt)
}

android {
    namespace = "com.example.datastore"
}

dependencies {
    api(libs.androidx.datastore.core)
    api(projects.core.model)
    implementation(libs.androidx.datastore.preferences)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso.core)
}
