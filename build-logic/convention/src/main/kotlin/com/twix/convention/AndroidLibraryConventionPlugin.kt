package com.twix.convention

import com.android.build.api.dsl.LibraryExtension
import com.twix.convention.extension.*
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryConventionPlugin : BuildLogicConventionPlugin({
    applyPlugins(
        "com.android.library",
        "org.jetbrains.kotlin.android",
        "de.mannodermaus.android-junit5"
    )

    extensions.configure<LibraryExtension> {
        configureAndroid(this)
        defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    dependencies {
        implementation(libs.library("kotlinx-coroutines-core"))
        testImplementation(libs.bundle("test-unit"))
        androidTestImplementation(libs.library("androidx-test-ext-junit"))
    }
})
