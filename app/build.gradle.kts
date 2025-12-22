plugins {
    alias(libs.plugins.twix.android.application)
    alias(libs.plugins.twix.koin)
}

android {
    namespace = "com.yapp.twix"

    defaultConfig {
        applicationId = "com.yapp.twix"
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
}

dependencies {
    implementation(projects.core.designSystem)
    implementation(projects.core.network)
    implementation(projects.core.navigation)
    implementation(projects.core.ui)
    implementation(projects.core.util)
    implementation(projects.data)
    implementation(projects.domain)
    implementation(projects.feature.login)
}
