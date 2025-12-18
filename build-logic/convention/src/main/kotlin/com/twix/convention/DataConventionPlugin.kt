package com.twix.convention

import com.twix.convention.extension.*

class DataConventionPlugin : BuildLogicConventionPlugin({
    applyPlugins(
        "twix.android.library",
        "org.jetbrains.kotlin.plugin.serialization",
        "twix.koin"
    )
})
