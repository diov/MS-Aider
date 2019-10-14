package io.github.diov.msaider

import android.webkit.WebSettings
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Headers
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * MSAider
 *
 * Created by Dio_V on 2019-10-13.
 * Copyright © 2019 diov.github.io. All rights reserved.
 */

class GamewithRecruiter {

    private val resultAdapter by lazy {
        val moshi = Moshi.Builder().build()
        moshi.adapter(RecruitResult::class.java)
    }
    private val client = OkHttpClient()
    private val userAgent by lazy {
        WebSettings.getDefaultUserAgent(AiderApp.app)
    }

    fun recruit(order: String, type: RecruitType, callback: ((Outcome<RecruitResult>) -> Unit)? = null) {
        val request = generateRequest(order, type)
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback?.invoke(Outcome.failure(e))
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val body = requireNotNull(response.body?.string())
                    val result = requireNotNull(resultAdapter.fromJson(body))
                    callback?.invoke(Outcome.success(result))
                } catch (e: Exception) {
                    callback?.invoke(Outcome.failure(e))
                }
            }
        })
    }

    private fun generateRequest(order: String, type: RecruitType): Request {
        val httpUrl = HttpUrl.Builder()
            .scheme("https")
            .host(HOST)
            .addPathSegments(PATH)
            .addQueryParameter(BODY_PARAMETER, order)
            .addQueryParameter(TAGS_PARAMETER, type.tag)
            .addQueryParameter(UID_PARAMETER, AiderApp.uuid)
            .addQueryParameter(LOCALE_PARAMETER, "zh-TW")
            .build()
        val headers = Headers.Builder()
            .add("Referer", "https://gamewith.tw/monsterstrike/lobby")
            .add("Origin", "https://gamewith.tw")
            .add("User-Agent", userAgent)
            .add("DNT", "1")
            .add("Sec-Fetch-Mode", "cors")
            .build()
        return Request.Builder()
            .get()
            .url(httpUrl)
            .headers(headers)
            .build()
    }
}

enum class RecruitType(val tag: String) {
    MAX_LUCK("僅限極運"),
    ONLY_CORRECT("僅限適正角色"),
    ANYONE("誰都可以")
}

@JsonClass(generateAdapter = true)
data class RecruitResult(
    @Json(name = "status") val status: Int,
    @Json(name = "recruitment_number") val recruitmentNumber: Int,
    @Json(name = "participation_number") val participationNumber: Int,
    @Json(name = "message") val message: String,
    @Json(name = "congestion_level") val congestionLevel: Int
)
