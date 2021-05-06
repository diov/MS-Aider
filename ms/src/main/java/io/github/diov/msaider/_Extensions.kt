@file:Suppress("NOTHING_TO_INLINE")

package io.github.diov.msaider

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.core.view.children
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

/**
 * Created by Dio_V on 2019/3/22.
 * Copyright © 2019 diov.github.io. All rights reserved.
 */

const val STRIKE_LINK = "Monster-Strike-Link"

inline fun Context.copyToClipboard(content: String) {
    val clipboardManager = getSystemService<ClipboardManager>()
    val clipData = ClipData.newPlainText(STRIKE_LINK, content)
    clipboardManager?.setPrimaryClip(clipData)
}

inline fun ChipGroup.actAsRadioGroup() {
    val chip = findViewById<Chip>(checkedChipId)
    if (chip != null) {
        children.forEach { it.isClickable = true }
        chip.isClickable = false
    }
}
