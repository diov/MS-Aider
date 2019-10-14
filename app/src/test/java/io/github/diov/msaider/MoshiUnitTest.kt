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
            {"status":1,"recruitment_number":1,"participation_number":0,"message":"","congestion_level":1}
        """.trimIndent()
        val adapter = Moshi.Builder().build().adapter(RecruitResult::class.java)
        val result = RecruitResult(1, 1, 0, "", 1)
        assertEquals(result, adapter.fromJson(json))
    }
}
