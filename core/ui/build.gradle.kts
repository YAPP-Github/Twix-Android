plugins {
    alias(libs.plugins.twix.android.library)
    alias(libs.plugins.twix.android.compose)
}

android {
    namespace = "com.twix.ui"
}

dependencies {
    implementation(projects.core.designSystem)
}
