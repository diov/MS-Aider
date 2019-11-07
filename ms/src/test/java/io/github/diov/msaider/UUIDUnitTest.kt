package io.github.diov.msaider

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.UUID

class UUIDUnitTest {
    @Test
    fun uuid_isCorrect() {
        val s = buildString {
            repeat(5) {
                append(UUID.randomUUID().toString().filter { it != '-' })
            }
        }.filterIndexed { index, _ -> index < 144 }
        assertEquals(144, s.count())
    }
}
