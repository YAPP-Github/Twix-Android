import java.util.Properties

plugins {
    alias(libs.plugins.twix.android.library)
    alias(libs.plugins.twix.koin)
    alias(libs.plugins.serialization)
    alias(libs.plugins.ktorfit)
    alias(libs.plugins.ksp)
}

val localProperties =
    Properties().apply {
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            load(localPropertiesFile.inputStream())
        }
    }

android {
    namespace = "com.twix.network"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        val devBaseUrl = localProperties.getProperty("dev_base_url")
        val prodBaseUrl = localProperties.getProperty("prod_base_url")

        buildConfigField("String", "DEV_BASE_URL", "\"$devBaseUrl\"")
        buildConfigField("String", "PROD_BASE_URL", "\"$prodBaseUrl\"")
    }
}

dependencies {
    implementation(libs.bundles.ktor)
    implementation(libs.ktorfit.lib)
}
