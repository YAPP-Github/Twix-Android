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
        // 기본 코루틴 의존성 (Android 모듈 공통)
        implementation(libs.library("kotlinx-coroutines-core"))
    }
})
