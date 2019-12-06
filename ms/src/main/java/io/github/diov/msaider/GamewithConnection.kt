package io.github.diov.msaider

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder

/**
 * MSAider
 *
 * Created by Dio_V on 2019-11-20.
 * Copyright © 2019 diov.github.io. All rights reserved.
 */

class GamewithConnection : ServiceConnection {

    private var connectionActive = false
    private var service: GamewithService? = null

    fun connect(context: Context) {
        if (connectionActive) return
        connectionActive = true
        val intent = Intent(context, GamewithService::class.java)
        context.bindService(intent, this, Context.BIND_AUTO_CREATE)
    }

    fun disconnect(context: Context) {
        if (connectionActive) try {
            context.unbindService(this)
        } catch (_: IllegalArgumentException) {
        }
        connectionActive = false
        service = null
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        service = null
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val binder = service as GamewithService.Binder
        this.service = binder.getService()
    }

    suspend fun recruit(order: String, type: RecruitType, count: Int, callback: (Outcome<RecruitResult>) -> Unit) {
        try {
            service?.recruit(order, type, count, callback)
        } catch (e: Exception) {
            ThirdPartySDKManager.trackException(e)
        }
    }
}
