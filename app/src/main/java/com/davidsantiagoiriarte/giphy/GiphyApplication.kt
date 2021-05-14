package com.davidsantiagoiriarte.giphy

import android.app.Application
import com.davidsantiagoiriarte.data.di.dbModule
import com.davidsantiagoiriarte.data.di.helpersModule
import com.davidsantiagoiriarte.data.di.networkModule
import com.davidsantiagoiriarte.data.di.repositoriesModule
import com.davidsantiagoiriarte.giphy.di.appModule
import com.davidsantiagoiriarte.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GiphyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GiphyApplication)
            modules(
                appModule,
                presentationModule,
                networkModule,
                repositoriesModule,
                dbModule,
                helpersModule
            )
        }
    }
}
