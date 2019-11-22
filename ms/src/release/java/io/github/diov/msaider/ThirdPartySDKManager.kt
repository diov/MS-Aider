package io.github.diov.msaider

import android.app.Application
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import okhttp3.Interceptor

/**
 * MSAider
 *
 * Created by Dio_V on 2019-10-16.
 * Copyright © 2019 diov.github.io. All rights reserved.
 */

object ThirdPartySDKManager {

    private const val APP_CENTER_KEY = "b3a61a95-b95d-4519-8495-007094a6d5a1"

    val interceptor: Interceptor? = null

    fun setup(application: Application) {
        AppCenter.start(application, APP_CENTER_KEY, Analytics::class.java, Crashes::class.java)
    }

    fun trackException(e: Exception) {
        Crashes.trackError(e)
    }
}
