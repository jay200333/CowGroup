plugins {
    `kotlin-dsl`
}

group = "com.example.cowgroup.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.compiler.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "cowgroup.android.application"
            implementationClass = "AndroidApplicationPlugin"
        }

        register("androidLibrary") {
            id = "cowgroup.android.library"
            implementationClass = "AndroidLibraryPlugin"
        }

        register("androidCompose") {
            id = "cowgroup.android.compose"
            implementationClass = "AndroidComposePlugin"
        }

        register("androidHilt") {
            id = "cowgroup.android.hilt"
            implementationClass = "HiltPlugin"
        }

        register("feature") {
            id = "cowgroup.feature"
            implementationClass = "CowGroupFeaturePlugin"
        }

        register("javaLibrary") {
            id = "cowgroup.java.library"
            implementationClass = "JavaLibraryPlugin"
        }
    }
}
