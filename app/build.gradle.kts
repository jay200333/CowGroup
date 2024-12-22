plugins {
    alias(libs.plugins.cowgroup.android.application)
    alias(libs.plugins.cowgroup.android.hilt)
// 구글 서비스 이용시 주석 제거
// id("com.google.gms.google-services")
}

android {
    namespace = "com.example.mycowgroup"

    defaultConfig {
        applicationId = "com.example.mycowgroup"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.feature.main)
    implementation(projects.feature.login)

    implementation(projects.core.designsystem)
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.core.model)
    implementation(projects.core.ui)
    implementation(projects.core.common)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}
