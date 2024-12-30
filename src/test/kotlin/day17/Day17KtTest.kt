package day17

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day17KtTest {

    val EXAMPLE = """
        Register A: 729
        Register B: 0
        Register C: 0

        Program: 0,1,5,4,3,0
    """.trimIndent()

    val EXAMPLE_PART_2 = """
        Register A: 2024
        Register B: 0
        Register C: 0

        Program: 0,3,5,4,3,0
    """.trimIndent()

    @Test
    fun part1() {
        assertEquals("4,6,3,5,6,3,5,2,1,0", part1(EXAMPLE))
    }

    @Test
    fun part2() {
        assertEquals(117440, part2(EXAMPLE_PART_2))
    }
}