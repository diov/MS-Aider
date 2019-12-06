package io.github.diov.msaider

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

class OrderBoardActivity : AppCompatActivity(), OrderAdapterDelegate {

    private val adapter = OrderAdapter(this)
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

    override fun onIconClicked(quest: Quest) {
        openChrome(quest.wikiUrl)
    }

    override fun onItemClicked(order: Order) {

    }
}
