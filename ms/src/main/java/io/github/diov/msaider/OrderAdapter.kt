package io.github.diov.msaider

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_order.view.*

/**
 * MSAider
 *
 * Created by Dio_V on 2019-11-16.
 * Copyright © 2019 diov.github.io. All rights reserved.
 */

class OrderAdapter(private val delegate: OrderAdapterDelegate) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    private var orderList: Array<Order> = emptyArray()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false))
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderList[position]
        holder.setup(order)
    }

    override fun getItemCount() = orderList.count()

    fun update(newOrders: Array<Order>) {
        val result = DiffUtil.calculateDiff(OrderDiffCallback(orderList, newOrders))
        result.dispatchUpdatesTo(this)
        orderList = newOrders
    }

    inner class OrderViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun setup(order: Order) {
            containerView.orderIcon.load(order.quest.icon)
            containerView.orderTag.text = order.quest.level
            containerView.orderTitle.text = order.quest.name
            containerView.orderLevel.text = order.conditions.tags[0]
            containerView.orderIcon.setOnClickListener { delegate.onIconClicked(order.quest) }
            containerView.setOnClickListener { delegate.onItemClicked(order) }
        }
    }
}

interface OrderAdapterDelegate {
    fun onIconClicked(quest: Quest)
    fun onItemClicked(order: Order)
}
