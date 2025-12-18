package com.twix.convention

import com.twix.convention.extension.*

class DataConventionPlugin : BuildLogicConventionPlugin({
    applyPlugins(
        "twix.android.library",
        "org.jetbrains.kotlin.plugin.serialization",
        "twix.koin"
    )
    
    // kotlinx-coroutines-core는 twix.android.library에서 이미 추가됨
})
