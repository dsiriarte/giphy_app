package com.davidsantiagoiriarte.giphy.di

import com.davidsantiagoiriarte.domain.logger.AppLogger
import com.davidsantiagoiriarte.giphy.logger.AppLoggerImpl
import org.koin.dsl.module

val appModule = module {
    single<AppLogger> {
        AppLoggerImpl()
    }
}
