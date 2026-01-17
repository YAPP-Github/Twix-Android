plugins {
    alias(libs.plugins.twix.android.library)
    alias(libs.plugins.twix.android.compose)
    alias(libs.plugins.twix.kermit)
}

android {
    namespace = "com.twix.ui"
}

dependencies {
    implementation(projects.core.designSystem)
}
