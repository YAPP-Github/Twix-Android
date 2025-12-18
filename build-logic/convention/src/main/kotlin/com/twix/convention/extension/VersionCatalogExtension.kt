package com.twix.convention.extension

import org.gradle.api.artifacts.ExternalModuleDependencyBundle
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionConstraint
import org.gradle.api.provider.Provider

internal fun VersionCatalog.bundle(bundleName: String): Provider<ExternalModuleDependencyBundle> {
    return findBundle(bundleName).orElseThrow {
        NoSuchElementException("Bundle with name $bundleName not found in the catalog")
    }
}

internal fun VersionCatalog.library(libraryName: String): Provider<MinimalExternalModuleDependency> {
    return findLibrary(libraryName).orElseThrow {
        NoSuchElementException("Library with name $libraryName not found in the catalog")
    }
}

internal fun VersionCatalog.version(versionName: String): VersionConstraint =
    findVersion(versionName).orElseThrow {
        NoSuchElementException("Version with name $versionName not found in the catalog")
    }
