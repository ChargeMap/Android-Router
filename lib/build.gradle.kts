plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    commonConfig()

    kotlinOptions.jvmTarget = "1.8"

    //buildFeatures.viewBinding = true
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}")
    
    testImplementation("junit:junit:4.13.1")
}