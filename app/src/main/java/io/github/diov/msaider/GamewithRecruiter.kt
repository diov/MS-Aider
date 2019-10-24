package io.github.diov.msaider

import android.webkit.WebSettings
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.HttpUrl
import okhttp3.Interceptor
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

    fun recruit(
        order: String,
        type: RecruitType,
        headCount: Int,
        callback: ((Outcome<RecruitResult>) -> Unit)? = null
    ) {
        val request = generateRequest(order, type, headCount)
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

    private fun generateRequest(order: String, type: RecruitType, headCount: Int): Request {
        val httpUrl = HttpUrl.Builder()
            .scheme("https")
            .host(HOST)
            .addPathSegments(RECRUIT_PATH)
            .build()
        val formBody = FormBody.Builder()
            .add(BODY_PARAMETER, order)
            .add(TAGS_PARAMETER, type.tag)
            .add(HEAD_COUNT_PARAMETER, "$headCount")
            .add(UID_PARAMETER, AiderApp.uuid)
            .add(LOCALE_PARAMETER, "zh-TW")
            .build()

        return Request.Builder()
            .url(httpUrl)
            .post(formBody)
            .build()
    }

    companion object {
        private val resultAdapter by lazy {
            Moshi.Builder().build().adapter(RecruitResult::class.java)
        }
        @Suppress("UNNECESSARY_SAFE_CALL")
        private val client by lazy {
            OkHttpClient.Builder()
                .addInterceptor(HeaderInterceptor())
                .apply { ThirdPartySDKManager.interceptor?.run(::addNetworkInterceptor) }
                .build()
        }
    }
}

internal class HeaderInterceptor : Interceptor {
    private val userAgent by lazy {
        WebSettings.getDefaultUserAgent(AiderApp.app)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().apply {
            addHeader("Referer", "https://gamewith.tw/monsterstrike/lobby")
            addHeader("Origin", "https://gamewith.tw")
            addHeader("User-Agent", userAgent)
            addHeader("DNT", "1")
            addHeader("Sec-Fetch-Mode", "cors")
        }.build()
        return chain.proceed(request)
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
    @Json(name = "launch_url") val launchUrl: String,
    @Json(name = "native_app_launch_url") val intentUrl: String,
    @Json(name = "message") val message: String,
    @Json(name = "expire_seconds") val expireSeconds: Int
)
