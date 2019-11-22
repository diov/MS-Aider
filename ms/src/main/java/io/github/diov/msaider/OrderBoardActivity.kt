package io.github.diov.msaider

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_order_board.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

    private val adapter = OrderAdapter()
    private val connection = GamewithConnection()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_board)

        connection.connect(this)
        setupView()
    }

    override fun onDestroy() {
        super.onDestroy()

        connection.disconnect(this)
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
