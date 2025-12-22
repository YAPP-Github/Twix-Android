package com.twix.convention

import com.twix.convention.extension.*
import org.gradle.kotlin.dsl.dependencies

class KoinConventionPlugin : BuildLogicConventionPlugin({
    dependencies {
        implementation(platform(libs.library("koin-bom")))
        implementation(libs.bundle("koin"))
    }
})
