plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
}

allprojects {
    apply {
        plugin(rootProject.libs.plugins.ktlint.get().pluginId)
    }
}
