package com

import android.app.Application
import com.aguskoll.di.initDI
import com.aguskoll.simpsons.di.appModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initDI(appModule = appModule)
    }
}