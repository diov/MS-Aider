package io.github.diov.msaider

import android.content.Context
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader

/**
 * MSAider
 *
 * Created by Dio_V on 2019-10-16.
 * Copyright © 2019 diov.github.io. All rights reserved.
 */

object FlipperManager {

    val networkPlugin = NetworkFlipperPlugin()

    fun setupFlipper(context: Context) {
        SoLoader.init(context, false)
        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(context)) {
            val client = AndroidFlipperClient.getInstance(context)
            client.addPlugin(networkPlugin)
            client.start()
        }
    }
}
