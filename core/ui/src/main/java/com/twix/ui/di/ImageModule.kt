package com.twix.ui.di

import com.twix.ui.image.ImageGenerator
import com.twix.ui.image.Rotator
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val imageModule =
    module {
        factory { Rotator(androidContext().contentResolver) }
        factory { ImageGenerator(androidContext().contentResolver, get<Rotator>()) }
    }
