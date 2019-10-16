package io.github.diov.msaider

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import java.util.UUID

/**
 * MSAider
 *
 * Created by Dio_V on 2019-10-14.
 * Copyright © 2019 diov.github.io. All rights reserved.
 */

class AiderApp : Application() {

    override fun onCreate() {
        super.onCreate()

        app = this
        uuid = generateUUID()

        FlipperManager.setupFlipper(app)
    }

    private fun generateUUID(): String {
        return buildString {
            repeat(5) {
                append(UUID.randomUUID().toString().filter { it != '-' })
            }
        }.filterIndexed { index, _ -> index < 143 }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var app: Context

        lateinit var uuid: String
    }
}
