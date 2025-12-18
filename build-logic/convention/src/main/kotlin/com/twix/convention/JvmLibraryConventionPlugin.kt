package com.twix.convention

import com.twix.convention.extension.*
import org.gradle.api.JavaVersion
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

class JvmLibraryConventionPlugin : BuildLogicConventionPlugin({
    applyPlugins("org.jetbrains.kotlin.jvm")

    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    extensions.configure<KotlinProjectExtension> {
        jvmToolchain(libs.version("java").requiredVersion.toInt())
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    dependencies {
        implementation(libs.library("kotlinx-coroutines-core-jvm"))
        testImplementation(libs.bundle("test-unit"))
    }
})
