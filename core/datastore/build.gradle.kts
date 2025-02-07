plugins {
    alias(libs.plugins.cowgroup.android.library)
    alias(libs.plugins.cowgroup.android.hilt)
}

android {
    namespace = "com.example.datastore"
}

dependencies {
    api(libs.androidx.datastore.core)
    api(libs.androidx.datastore.preferences)
    api(projects.core.model)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso.core)
}
