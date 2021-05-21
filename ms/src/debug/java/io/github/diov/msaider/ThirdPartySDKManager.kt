package io.github.diov.msaider

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader
import java.lang.Exception

/**
 * MSAider
 *
 * Created by Dio_V on 2019-10-16.
 * Copyright © 2019 diov.github.io. All rights reserved.
 */

object ThirdPartySDKManager {

    private val networkPlugin = NetworkFlipperPlugin()
    val interceptor = FlipperOkhttpInterceptor(networkPlugin)

    fun setup(application: Application) {
        val context = application.applicationContext
        SoLoader.init(context, false)
        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(context)) {
            val client = AndroidFlipperClient.getInstance(context)
            client.addPlugin(networkPlugin)
            client.start()
        }
    }

    fun trackException(e: Exception) {
        e.printStackTrace()
    }
}
