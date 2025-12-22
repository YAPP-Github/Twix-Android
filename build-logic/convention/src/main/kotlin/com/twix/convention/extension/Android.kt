package com.twix.convention.extension

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

internal fun Project.configureAndroid(extension: CommonExtension<*, *, *, *, *, *>) {
    val javaVersionInt = libs.version("java").requiredVersion.toInt()
    val javaVersion = JavaVersion.toVersion(javaVersionInt)

    extension.apply {
        compileSdk = libs.version("compileSdk").requiredVersion.toInt()

        defaultConfig {
            minSdk = libs.version("minSdk").requiredVersion.toInt()
        }

        compileOptions {
            sourceCompatibility = javaVersion
            targetCompatibility = javaVersion
        }

        extensions.configure<KotlinProjectExtension> {
            jvmToolchain(javaVersionInt)
        }
    }
}
