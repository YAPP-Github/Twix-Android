package com.yapp.twix

import android.app.Application
import com.yapp.twix.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TwixApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin(
            context = this
        )
    }
}
