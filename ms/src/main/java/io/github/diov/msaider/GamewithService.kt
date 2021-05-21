package io.github.diov.msaider

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

/**
 * MSAider
 *
 * Created by Dio_V on 2019-11-20.
 * Copyright © 2019 diov.github.io. All rights reserved.
 */

class GamewithService : LifecycleService() {

    private val binder = Binder()
    private val recruiter = GamewithRecruiter()
    private val notification = ServiceNotification()
    private val receiver = ActionReceiver()
    private val notificationManager by lazy { getSystemService<NotificationManager>()!! }

    private var reminderJob: Job? = null

    override fun onCreate() {
        super.onCreate()

        val filter = IntentFilter().apply {
            addAction(ACTION_CANCEL)
            addAction(ACTION_REMOVE)
        }
        registerReceiver(receiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()

        unregisterReceiver(receiver)
    }

    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)

        return binder
    }

    suspend fun recruit(order: String, type: RecruitType, count: Int, callback: (Outcome<RecruitResult>) -> Unit) {
        val outcome = recruiter.recruit(order, type, count)
        if (outcome is Outcome.Success) {
            val expireSeconds = outcome.value.expireSeconds
            notification.createRecruitNotification(expireSeconds)
        }
        callback(outcome)
    }

    inner class Binder : android.os.Binder() {
        fun getService() = this@GamewithService
    }

    inner class ActionReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                ACTION_CANCEL -> {
                    reminderJob?.cancel()
                    context.getSystemService<NotificationManager>()!!.cancel(CHANNEL_ID)
                }
                ACTION_REMOVE -> reminderJob?.cancel()
            }
        }
    }

    inner class ServiceNotification {

        private val service = this@GamewithService

        fun createRecruitNotification(expiredSeconds: Int) {
            val builder = NotificationCompat.Builder(service, CHANNEL)
                .setContentTitle(service.getString(R.string.notification_recruit_processing))
                .setSmallIcon(R.drawable.ic_dashboard_black_24dp)
                .setDeleteIntent(generateRemoveIntent())
                .addAction(0, service.getString(R.string.notification_cancel), generateCancelIntent())
            notificationManager.notify(CHANNEL_ID, builder.build())
            reminderJob?.cancel()
            reminderJob = lifecycleScope.launch {
                builder.setProgress(expiredSeconds, 0, false)
                notificationManager.notify(CHANNEL_ID, builder.build())

                reminder(expiredSeconds).collect {
                    builder.setProgress(expiredSeconds, it, false)
                    notificationManager.notify(CHANNEL_ID, builder.build())
                }
                builder.setProgress(0, 0, false)
                notificationManager.notify(CHANNEL_ID, builder.build())
                alertRecruitExpired()
            }
        }

        private fun reminder(duration: Int): Flow<Int> = flow {
            for (i in 1..duration) {
                delay(1000)
                emit(i)
            }
        }

        private fun alertRecruitExpired() {
            val builder = NotificationCompat.Builder(service, CHANNEL)
                .setContentTitle(service.getString(R.string.notification_recruit_finished))
                .setSmallIcon(R.drawable.ic_dashboard_black_24dp)
                .setTimeoutAfter(10_000)
            notificationManager.notify(CHANNEL_ID, builder.build())
        }

        private fun generateCancelIntent(): PendingIntent {
            val intent = Intent(ACTION_CANCEL).setPackage(packageName)
            return PendingIntent.getBroadcast(service, 0, intent, 0)
        }

        private fun generateRemoveIntent(): PendingIntent {
            val intent = Intent(ACTION_REMOVE).setPackage(packageName)
            return PendingIntent.getBroadcast(service, 0, intent, 0)
        }
    }

    companion object {
        const val CHANNEL = "aider-service"
        const val CHANNEL_ID = 4399

        const val ACTION_CANCEL = "cancel"
        const val ACTION_REMOVE = "remove"
    }
}



