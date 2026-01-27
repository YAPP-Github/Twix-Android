package com.twix.ui.di

import com.twix.ui.toast.ToastManager
import org.koin.dsl.module

val uiModule =
    module {
        single { ToastManager() }
    }
