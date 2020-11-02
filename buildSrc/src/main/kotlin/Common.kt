import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import java.util.*

fun BaseExtension.commonConfig(properties: Properties) {
    compileSdkVersion(30)
    buildToolsVersion("30.0.2")

    defaultConfig {
        versionCode = properties.getProperty("version.code").toString().toInt()
        versionName = properties.getProperty("version.name").toString()
        minSdkVersion(21)
        targetSdkVersion(30)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    listOf(
        "debug",
        "test",
        "release",
        "androidTest",
        "main"
    ).forEach {
        sourceSets.getByName(it).java.srcDirs("src/$it/kotlin")
    }

    compileOptions.sourceCompatibility = JavaVersion.VERSION_1_8
    compileOptions.targetCompatibility = JavaVersion.VERSION_1_8

    testOptions.unitTests.isReturnDefaultValues = false
}