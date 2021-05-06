package io.github.diov.msaider

import android.webkit.WebSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.FormBody
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * MSAider
 *
 * Created by Dio_V on 2019-10-13.
 * Copyright © 2019 diov.github.io. All rights reserved.
 */

class GamewithRecruiter {

    suspend fun recruit(order: String, type: RecruitType, headCount: Int) = withContext(Dispatchers.IO) {
        execCatching {
            val request = recruitRequest(order, type, headCount)
            val response = client.newCall(request).cancellableExecute()
            val body = requireNotNull(response.body?.string())
            json.decodeFromString(RecruitResult.serializer(), body)
        }
    }

    suspend fun fetch() = withContext(Dispatchers.IO) {
        execCatching {
            val request = fetchRequest()
            val response = client.newCall(request).cancellableExecute()
            val body = requireNotNull(response.body?.string())
            json.decodeFromString(OrderBoard.serializer(), body)
        }
    }

    private fun recruitRequest(order: String, type: RecruitType, headCount: Int): Request {
        val httpUrl = HttpUrl.Builder()
            .scheme(HTTPS_SCHEME)
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
            .get()
            .post(formBody)
            .build()
    }

    private fun fetchRequest(): Request {
        val httpUrl = HttpUrl.Builder()
            .scheme(HTTPS_SCHEME)
            .host(HOST)
            .addPathSegment(RECRUIT_PATH)
            .addEncodedQueryParameter(CATEGORY_PARAMETER, "1")
            .addEncodedQueryParameter(LOCALE_PARAMETER, "zh-TW")
            .addEncodedQueryParameter(UID_PARAMETER, AiderApp.uuid)
            .build()
        return Request.Builder()
            .url(httpUrl)
            .get()
            .build()
    }

    companion object {
        private val json by lazy { Json {} }

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

suspend inline fun Call.cancellableExecute(): Response = suspendCancellableCoroutine { continuation ->
    continuation.invokeOnCancellation {
        cancel()
    }
    try {
        continuation.resume(execute())
    } catch (t: Throwable) {
        continuation.resumeWithException(t)
    }
}
