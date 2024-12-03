package day1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
class Day1KtTest {

    val EXAMPLE = """
        3   4
        4   3
        2   5
        1   3
        3   9
        3   3
    """.trimIndent()

    @Test
    fun part1() {
        assertEquals(11L, part1(EXAMPLE))
    }

    @Test
    fun part2() {
        assertEquals(31L, part2(EXAMPLE))
    }
}