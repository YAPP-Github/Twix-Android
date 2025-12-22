package com.twix.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.twix.convention.extension.*
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidComposeConventionPlugin : BuildLogicConventionPlugin({
    applyPlugins("org.jetbrains.kotlin.plugin.compose")

    pluginManager.withPlugin("com.android.application") {
        extensions.configure<ApplicationExtension> {
            configureCompose(this)
        }
    }

    pluginManager.withPlugin("com.android.library") {
        extensions.configure<LibraryExtension> {
            configureCompose(this)
        }
    }

    dependencies {
        val bom = platform(libs.library("compose-bom"))
        implementation(bom)
        implementation(libs.bundle("compose"))
        debugImplementation(libs.bundle("compose-debug"))
    }
})
