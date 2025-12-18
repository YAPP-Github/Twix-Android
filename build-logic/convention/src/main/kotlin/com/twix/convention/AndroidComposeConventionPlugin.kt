package com.twix.convention

import com.android.build.api.dsl.LibraryExtension
import com.twix.convention.extension.*
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidComposeConventionPlugin : BuildLogicConventionPlugin({
    applyPlugins("org.jetbrains.kotlin.plugin.compose")

    extensions.configure<LibraryExtension> {
        configureCompose(this)
    }

    dependencies {
        val bom = platform(libs.library("compose-bom"))
        implementation(bom)
        implementation(libs.bundle("compose"))
        debugImplementation(libs.bundle("compose-debug"))
    }
})
