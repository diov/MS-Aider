package io.github.diov.msaider

import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.content.getSystemService
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

        updateNotificationChannels()
        ThirdPartySDKManager.setup(this)
    }

    private fun updateNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = app.getSystemService<NotificationManager>()!!
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    GamewithService.CHANNEL, app.getText(R.string.service_gamewith),
                    NotificationManager.IMPORTANCE_LOW
                )
            )
        }
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
