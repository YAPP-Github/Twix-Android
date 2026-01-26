plugins {
    alias(libs.plugins.twix.feature)
}

android {
    namespace = "com.twix.task_certification"
}
dependencies {
    implementation(libs.bundles.cameraX)
}
