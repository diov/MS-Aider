package io.github.diov.msaider

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_order_board.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

/**
 * MSAider
 *
 * Created by Dio_V on 2019-03-23.
 * Copyright © 2019 diov.github.io. All rights reserved.
 */

class OrderBoardActivity : AppCompatActivity() {

    private fun sendIntent() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = """line://msg/text/戰友募集中！
讓我們一起並肩作戰，齊心協力突破難關！
「暗動的生命危機迷宮(美顏！福笑怪物)」
https://static.tw.monster-strike.com/sns/?pass_code=MjQzMTgyMjU1&f=line
↑點擊這個網址馬上一起連線作戰！""".trimIndent().toUri()
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun displayNotification() {
        val channel = NotificationChannel("1024", "MSAider", NotificationManager.IMPORTANCE_DEFAULT)
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.createNotificationChannel(channel)

        val builder = NotificationCompat.Builder(this, "MSAider")
            .setChannelId(channel.id)
            .setSmallIcon(R.drawable.ic_home_black_24dp)
            .setWhen(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(10))
            .setUsesChronometer(true)
            .setContentTitle("Hello")
            .setContentText("World!")
        NotificationManagerCompat.from(this).apply {
            notify(1, builder.build())
        }
    }

    private val adapter = OrderAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_board)

        setupView()
    }

    private fun setupView() {
        orderListView.layoutManager = LinearLayoutManager(this)
        orderListView.adapter = adapter
        orderListView.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))

        refreshButton.setOnClickListener {
            refreshOrder()
        }
    }

    private fun refreshOrder() {
        lifecycleScope.launch(Dispatchers.Main) {
            when (val outcome = GamewithRecruiter().fetch()) {
                is Outcome.Success -> {
                    adapter.update(outcome.value.orders)
                }
                is Outcome.Failure -> {
                    outcome.exception.printStackTrace()
                }
            }
        }
    }
}
