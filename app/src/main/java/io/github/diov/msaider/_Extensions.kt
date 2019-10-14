package io.github.diov.msaider

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.core.content.getSystemService

/**
 * Created by Dio_V on 2019/3/22.
 * Copyright © 2019 diov.github.io. All rights reserved.
 */

private const val STRIKE_LINK = "Monster-Strike-Link"

fun Context.copyToClipboard(content: String) {
    val clipboardManager = getSystemService<ClipboardManager>()
    val clipData = ClipData.newPlainText(STRIKE_LINK, content)
    clipboardManager?.setPrimaryClip(clipData)
}
