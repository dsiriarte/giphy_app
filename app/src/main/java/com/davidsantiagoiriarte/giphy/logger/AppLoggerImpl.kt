package com.davidsantiagoiriarte.giphy.logger

import android.util.Log
import com.davidsantiagoiriarte.domain.logger.AppLogger

class AppLoggerImpl : AppLogger {
    override fun logError(exception: Exception) {
        //LOG ERROR ON CRASHLYTICS, BUGSNAG OR OTHER LOG SERVICE
        exception.message?.let { Log.e("Giphy", it) }
    }
}
