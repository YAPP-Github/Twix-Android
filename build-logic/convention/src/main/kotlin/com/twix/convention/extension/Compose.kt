package com.twix.convention.extension

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureCompose(extension: CommonExtension<*, *, *, *, *, *>) {
    extension.apply {
        buildFeatures {
            compose = true
        }

        dependencies {
            val composeBom = libs.library("compose-bom")
            implementation(platform(composeBom))
            implementation(libs.bundle("compose"))

            androidTestImplementation(platform(composeBom))
            androidTestImplementation(libs.library("compose-ui-test-junit4"))
            debugImplementation(libs.bundle("compose-debug"))

            implementation(libs.library("material"))
        }
    }
}
