import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion

fun BaseExtension.commonConfig() {
    compileSdkVersion(30)
    buildToolsVersion("30.0.2")

    defaultConfig {
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