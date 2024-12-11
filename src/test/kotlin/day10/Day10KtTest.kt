package day10

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
class Day10KtTest {

    val EXAMPLE = """
        89010123
        78121874
        87430965
        96549874
        45678903
        32019012
        01329801
        10456732
    """.trimIndent()

    @Test
    fun part1() {
        assertEquals(36, day10.part1(EXAMPLE))
    }

    @Test
    fun part2() {
        assertEquals(81, day10.part2(EXAMPLE))
    }
}