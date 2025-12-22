package com.twix.convention

import com.twix.convention.extension.implementation
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class FeatureConventionPlugin : BuildLogicConventionPlugin({
    apply<AndroidLibraryConventionPlugin>()
    apply<KoinConventionPlugin>()
    apply<AndroidComposeConventionPlugin>()

    dependencies {
        implementation(project(":core:design-system"))
        implementation(project(":core:navigation"))
        implementation(project(":core:ui"))
        implementation(project(":domain"))
    }
})
