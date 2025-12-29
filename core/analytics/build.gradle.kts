plugins {
    alias(libs.plugins.twix.android.library)
    alias(libs.plugins.twix.koin)
}

android {
    namespace = "com.twix.analytics"
}

dependencies {
    implementation(platform(libs.google.firebase.bom))
    api(libs.google.firebase.analytics)
}
