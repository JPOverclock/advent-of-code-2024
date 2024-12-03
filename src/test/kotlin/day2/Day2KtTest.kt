package day2

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
class Day2KtTest {

    val EXAMPLE = """
        7 6 4 2 1
        1 2 7 8 9
        9 7 6 2 1
        1 3 2 4 5
        8 6 4 4 1
        1 3 6 7 9
    """.trimIndent()

    @Test
    fun part1() {
        assertEquals(2, day2.part1(EXAMPLE))
    }

    @Test
    fun part2() {
        assertEquals(4, day2.part2(EXAMPLE))
    }
}