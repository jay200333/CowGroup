
import com.android.build.api.dsl.ApplicationExtension
import com.example.convention.extension.configureComposeAndroid
import com.example.convention.extension.configureKotlinAndroid
import com.example.convention.extension.getBundle
import com.example.convention.extension.getVersion
import com.example.convention.extension.implementation
import com.example.convention.extension.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.application")

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                configureComposeAndroid(this)

                with(defaultConfig) {
                    targetSdk = libs.getVersion("targetSdk").requiredVersion.toInt()
                    versionCode = libs.getVersion("versionCode").requiredVersion.toInt()
                    versionName = libs.getVersion("versionName").requiredVersion
                }
                dependencies {
                    implementation(libs.getBundle("navigation"))
                }
            }
        }
    }
}
