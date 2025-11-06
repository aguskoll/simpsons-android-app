package com.aguskoll

import android.app.Application
import com.aguskoll.di.initDI
import com.aguskoll.androidcomposebase.di.appModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initDI(appModule = appModule)
    }
}
