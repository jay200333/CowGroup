plugins {
    alias(libs.plugins.cowgroup.android.library)
    alias(libs.plugins.cowgroup.android.hilt)
}

android {
    namespace = "com.example.common"
}

dependencies {
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}
