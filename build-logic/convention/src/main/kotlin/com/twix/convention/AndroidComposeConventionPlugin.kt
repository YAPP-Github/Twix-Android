package com.twix.convention

import com.android.build.api.dsl.LibraryExtension
import com.twix.convention.extension.*
import org.gradle.kotlin.dsl.configure

class AndroidComposeConventionPlugin : BuildLogicConventionPlugin({
    applyPlugins("org.jetbrains.kotlin.plugin.compose")

    extensions.configure<LibraryExtension> {
        configureCompose(this)
    }
})
