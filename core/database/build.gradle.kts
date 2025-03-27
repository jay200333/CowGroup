plugins {
    alias(libs.plugins.cowgroup.android.library)
    alias(libs.plugins.cowgroup.android.hilt)
    alias(libs.plugins.cowgroup.android.room)
}

android {
    namespace = "com.example.database"
}

dependencies {
    api(projects.core.model)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso.core)
}