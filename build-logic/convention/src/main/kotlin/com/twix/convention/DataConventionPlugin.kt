package com.twix.convention

import com.twix.convention.extension.*
import org.gradle.kotlin.dsl.dependencies

class DataConventionPlugin : BuildLogicConventionPlugin({
    applyPlugins(
        "twix.android.library",
        "org.jetbrains.kotlin.plugin.serialization",
        "twix.koin"
    )

    dependencies {
        implementation(project(":domain"))
        implementation(project(":core:network"))
    }
})
