package com.yedajiang44.cognition

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun toHexString() {
        assertEquals("ffffff", 0xffffff.toString(16))
    }

    @Test
    fun padLeft() {
        assertEquals("000001", String.format("%6s", "1").replace(" ", "0"))
    }
}
