package day6

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day6KtTest {

    val EXAMPLE = """
        ....#.....
        .........#
        ..........
        ..#.......
        .......#..
        ..........
        .#..^.....
        ........#.
        #.........
        ......#...
    """.trimIndent()

    @Test
    fun part1() {
        assertEquals(41, day6.part1(EXAMPLE))
    }

    @Test
    fun part2() {
        assertEquals(6, day6.part2(EXAMPLE))
    }
}