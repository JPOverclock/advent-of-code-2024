package day9

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day9KtTest {

    val EXAMPLE = "2333133121414131402"

    @Test
    fun part1() {
        assertEquals(1928, day9.part1(EXAMPLE))
    }

    @Test
    fun part2() {
        assertEquals(2858, day9.part2(EXAMPLE))
    }
}