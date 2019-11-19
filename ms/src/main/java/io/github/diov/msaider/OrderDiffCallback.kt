package io.github.diov.msaider

import androidx.recyclerview.widget.DiffUtil

/**
 * MSAider
 *
 * Created by Dio_V on 2019-11-17.
 * Copyright © 2019 diov.github.io. All rights reserved.
 */

class OrderDiffCallback(private val oldList: Array<Order>, private val newList: Array<Order>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return old.quest.name == new.quest.name
    }

    override fun getOldListSize() = oldList.count()

    override fun getNewListSize() = newList.count()

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return old.participation == new.participation
            && old.recruitment == new.recruitment
    }

}
