package com.twix.convention

import com.android.build.api.dsl.ApplicationExtension
import com.twix.convention.extension.applyPlugins
import com.twix.convention.extension.configureAndroid
import com.twix.convention.extension.implementation
import com.twix.convention.extension.library
import com.twix.convention.extension.libs
import com.twix.convention.extension.version
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationConventionPlugin : BuildLogicConventionPlugin({
    applyPlugins("com.android.application", "org.jetbrains.kotlin.android")
    apply<AndroidComposeConventionPlugin>()

    extensions.configure<ApplicationExtension> {
        configureAndroid(this)

        defaultConfig {
            targetSdk = libs.version("targetSdk").requiredVersion.toInt()
        }
    }

    dependencies {
        implementation(libs.library("kotlinx-serialization-json"))
    }
})
