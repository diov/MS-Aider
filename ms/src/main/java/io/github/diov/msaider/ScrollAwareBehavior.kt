package io.github.diov.msaider

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * MSAider
 *
 * Created by Dio_V on 2019-11-19.
 * Copyright © 2019 diov.github.io. All rights reserved.
 */

class ScrollAwareBehavior(context: Context?, attrs: AttributeSet?) : FloatingActionButton.Behavior(context, attrs) {

    override fun layoutDependsOn(parent: CoordinatorLayout, child: FloatingActionButton, dependency: View): Boolean {
        return dependency is RecyclerView
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: FloatingActionButton,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: FloatingActionButton,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        child.hide()
    }

    override fun onStopNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: FloatingActionButton,
        target: View,
        type: Int
    ) {
        child.show()
    }
}
