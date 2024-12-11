package day11

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day11KtTest {

    val EXAMPLE = "125 17"

    @Test
    fun part1() {
        assertEquals(55312, day11.part1(EXAMPLE))
    }

    @Test
    fun part2() {
        assertEquals(65601038650482, day11.part2(EXAMPLE))
    }
}