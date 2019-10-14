package io.github.diov.msaider

import okhttp3.HttpUrl
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class HttpUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun httpUrl_isCorrect() {
        val body = """
            //msg/text/戰友募集中！
            讓我們一起並肩作戰，齊心協力突破難關！
            「大蛇闖江湖 ～尋求宿敵～(究極)」
            https://static.tw.monster-strike.com/sns/?pass_code=MjQzMTgyMjU1&f=line
            ↑點擊這個網址馬上一起連線作戰！
        """.trimIndent()
        val tag = """僅限極運"""
        val uid =
            """LL2v5WTHs3Z9LbEu6bbB6L2DqGdM59hjlHX3r3cw0P0122AVH1ApX-p--4VTG5HQgz7EVAIfpKh6qRBpA55PzU1xMUg1WTFLeWh5RnBUdmFSeEZvTzdSeXFaanNuMDU4R280VHlCb3MtWk0"""
        val locale = """zh-TW"""
        val httpUrl = HttpUrl.Builder()
            .scheme("https")
            .host("matching.gamewithservice.jp")
            .addPathSegments("api/v1/congestion")
            .addQueryParameter("body", body)
            .addQueryParameter("conditions[tags][]", tag)
            .addQueryParameter("uid", uid)
            .addQueryParameter("locale", locale)
            .build()
        val url =
            "https://matching.gamewithservice.jp/api/v1/congestion?body=%2F%2Fmsg%2Ftext%2F%E6%88%B0%E5%8F%8B%E5%8B%9F%E9%9B%86%E4%B8%AD%EF%BC%81%0A%E8%AE%93%E6%88%91%E5%80%91%E4%B8%80%E8%B5%B7%E4%B8%A6%E8%82%A9%E4%BD%9C%E6%88%B0%EF%BC%8C%E9%BD%8A%E5%BF%83%E5%8D%94%E5%8A%9B%E7%AA%81%E7%A0%B4%E9%9B%A3%E9%97%9C%EF%BC%81%0A%E3%80%8C%E5%A4%A7%E8%9B%87%E9%97%96%E6%B1%9F%E6%B9%96+%EF%BD%9E%E5%B0%8B%E6%B1%82%E5%AE%BF%E6%95%B5%EF%BD%9E(%E7%A9%B6%E6%A5%B5)%E3%80%8D%0Ahttps%3A%2F%2Fstatic.tw.monster-strike.com%2Fsns%2F%3Fpass_code%3DMjQzMTgyMjU1%26f%3Dline%0A%E2%86%91%E9%BB%9E%E6%93%8A%E9%80%99%E5%80%8B%E7%B6%B2%E5%9D%80%E9%A6%AC%E4%B8%8A%E4%B8%80%E8%B5%B7%E9%80%A3%E7%B7%9A%E4%BD%9C%E6%88%B0%EF%BC%81&conditions%5Btags%5D%5B%5D=%E5%83%85%E9%99%90%E6%A5%B5%E9%81%8B&uid=LL2v5WTHs3Z9LbEu6bbB6L2DqGdM59hjlHX3r3cw0P0122AVH1ApX-p--4VTG5HQgz7EVAIfpKh6qRBpA55PzU1xMUg1WTFLeWh5RnBUdmFSeEZvTzdSeXFaanNuMDU4R280VHlCb3MtWk0&locale=zh-TW"

        assertEquals(url, httpUrl.toUri().toString())
    }
}
