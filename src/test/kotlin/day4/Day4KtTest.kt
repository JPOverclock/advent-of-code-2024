package day4

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day4KtTest {

    val EXAMPLE = """
        MMMSXXMASM
        MSAMXMSMSA
        AMXSXMAAMM
        MSAMASMSMX
        XMASAMXAMM
        XXAMMXXAMA
        SMSMSASXSS
        SAXAMASAAA
        MAMMMXMMMM
        MXMXAXMASX
    """.trimIndent()

    @Test
    fun part1() {
        assertEquals(18, day4.part1(EXAMPLE))
    }

    @Test
    fun part2() {
        assertEquals(9, day4.part2(EXAMPLE))
    }
}