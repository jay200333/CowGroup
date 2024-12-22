import com.example.convention.extension.androidTestImplementation
import com.example.convention.extension.getBundle
import com.example.convention.extension.implementation
import com.example.convention.extension.libs
import com.example.convention.extension.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class CowGroupFeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("com.android.library")
                apply("cowgroup.android.hilt")
                apply("cowgroup.android.compose")
            }

            dependencies {
                implementation(project(":core:designsystem"))
                implementation(project(":core:model"))
                testImplementation(libs.getBundle("test"))
            }
        }
    }
}