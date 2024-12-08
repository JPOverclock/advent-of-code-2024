package day8

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day8KtTest {

    val EXAMPLE = """
        ............
        ........0...
        .....0......
        .......0....
        ....0.......
        ......A.....
        ............
        ............
        ........A...
        .........A..
        ............
        ............
    """.trimIndent()

    val PART_2_SMALL_EXAMPLE = """
        T.........
        ...T......
        .T........
        ..........
        ..........
        ..........
        ..........
        ..........
        ..........
        ..........
    """.trimIndent()

    @Test
    fun part1() {
        assertEquals(14, day8.part1(EXAMPLE))
    }

    @Test
    fun part2() {
        assertEquals(9, day8.part2(PART_2_SMALL_EXAMPLE))
        assertEquals(34, day8.part2(EXAMPLE))
    }
}