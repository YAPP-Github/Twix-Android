plugins {
    alias(libs.plugins.twix.android.library)
    alias(libs.plugins.twix.android.compose)
    alias(libs.plugins.twix.kermit)
    alias(libs.plugins.twix.koin)
}

android {
    namespace = "com.twix.ui"
}

dependencies {
    implementation(projects.core.designSystem)
    implementation(projects.domain)
}
