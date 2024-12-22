pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MyCowGroup"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
gradle.startParameter.excludedTaskNames.addAll(listOf(":build-logic:convention:testClasses"))
include(":app")

include(":feature:login")
include(":feature:main")

include(":core:domain")
include(":core:data")
include(":core:designsystem")
include(":core:model")
include(":core:ui")
include(":core:common")
