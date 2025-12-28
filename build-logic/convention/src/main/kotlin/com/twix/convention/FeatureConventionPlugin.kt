package com.twix.convention

import com.twix.convention.extension.implementation
import com.twix.convention.extension.library
import com.twix.convention.extension.libs
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

        val bom = libs.library("google-firebase-bom")
        implementation(platform(bom))
        implementation(libs.library("google-firebase-crashlytics"))
        implementation(libs.library("google-firebase-analytics"))
    }
})
