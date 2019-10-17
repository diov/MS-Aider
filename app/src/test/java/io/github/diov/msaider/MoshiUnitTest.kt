package io.github.diov.msaider

import com.squareup.moshi.Moshi
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MoshiUnitTest {

    @Test
    fun json_isCorrect() {
        val json = """
            {"status":1,
            "message":"",
            "launch_url":"https://static.tw.monster-strike.com/sns/?pass_code=MjQzMTgyMjU1\u0026f=line",
            "native_app_launch_url":"intent://joingame/?join=MjQzMTgyMjU1#Intent;scheme=monsterstrike-tw-app;end",
            "expire_seconds":90}
        """.trimIndent()
        val adapter = Moshi.Builder().build().adapter(RecruitResult::class.java)
        val result =
            RecruitResult(
                1,
                "https://static.tw.monster-strike.com/sns/?pass_code=MjQzMTgyMjU1\u0026f=line",
                "intent://joingame/?join=MjQzMTgyMjU1#Intent;scheme=monsterstrike-tw-app;end",
                "",
                90
            )
        assertEquals(result, adapter.fromJson(json))
    }
}
