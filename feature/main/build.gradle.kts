plugins {
    alias(libs.plugins.cowgroup.feature)
}

android {
    namespace = "com.example.main"
}

dependencies {
    implementation(libs.kotlinx.immutable)
    implementation(projects.feature.login)
    implementation(projects.feature.home)
    implementation(projects.feature.map)
    implementation(projects.feature.mypage)
    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(libs.androidx.core.ktx)
    implementation(project(":core:navigation"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}
