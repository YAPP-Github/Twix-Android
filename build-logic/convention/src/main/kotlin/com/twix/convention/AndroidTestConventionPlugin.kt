package com.twix.convention

import com.twix.convention.extension.*
import org.gradle.kotlin.dsl.dependencies

class AndroidTestConventionPlugin : BuildLogicConventionPlugin({
    dependencies {
        testImplementation(libs.bundle("test-unit"))
        androidTestImplementation(libs.library("androidx-test-ext-junit"))
    }
})
