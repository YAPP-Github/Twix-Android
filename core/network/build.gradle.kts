plugins {
    alias(libs.plugins.twix.android.library)
    alias(libs.plugins.twix.koin)
    alias(libs.plugins.serialization)
}

android {
    namespace = "com.twix.network"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.bundles.network)
}
