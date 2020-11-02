plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    commonConfig()

    kotlinOptions.jvmTarget = "1.8"

    //buildFeatures.viewBinding = true
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}")
    implementation("androidx.fragment:fragment-ktx:1.2.5")

    testImplementation("junit:junit:4.13.1")
}