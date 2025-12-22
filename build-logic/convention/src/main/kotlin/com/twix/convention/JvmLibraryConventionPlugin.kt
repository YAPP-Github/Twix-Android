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

    val javaVersionInt = libs.version("java").requiredVersion.toInt()
    val javaVersion = JavaVersion.toVersion(javaVersionInt)

    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    extensions.configure<KotlinProjectExtension> {
        jvmToolchain(javaVersionInt)
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    dependencies {
        implementation(libs.library("kotlinx-coroutines-core-jvm"))
        testImplementation(libs.bundle("test-unit"))
    }
})
