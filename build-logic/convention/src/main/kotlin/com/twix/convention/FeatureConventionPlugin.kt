package com.twix.convention

import com.twix.convention.extension.applyPlugins
import com.twix.convention.extension.implementation
import com.twix.convention.extension.testImplementation
import org.gradle.kotlin.dsl.dependencies

class FeatureConventionPlugin : BuildLogicConventionPlugin({
    applyPlugins(
        "twix.android.library",
        "twix.android.compose",
        "twix.android.test",
        "twix.koin",
    )

    dependencies {
        implementation(project(":core:design-system"))
        implementation(project(":core:navigation"))
        implementation(project(":core:ui"))
        implementation(project(":domain"))
        testImplementation(project(":core:test"))
    }
})
