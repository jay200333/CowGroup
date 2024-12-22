import com.android.build.gradle.LibraryExtension
import com.example.convention.extension.configureKotlinAndroid
import com.example.convention.extension.configureKotlinCoroutine
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                configureKotlinCoroutine(this)
            }
        }
    }
}