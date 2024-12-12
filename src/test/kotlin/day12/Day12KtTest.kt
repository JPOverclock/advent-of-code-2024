package day12

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day12KtTest {

    val SMALL_EXAMPLE = """
        AAAA
        BBCD
        BBCC
        EEEC
    """.trimIndent()

    val INNER_EXAMPLE = """
        OOOOO
        OXOXO
        OOOOO
        OXOXO
        OOOOO
    """.trimIndent()

    val EXAMPLE = """
        RRRRIICCFF
        RRRRIICCCF
        VVRRRCCFFF
        VVRCCCJFFF
        VVVVCJJCFE
        VVIVCCJJEE
        VVIIICJJEE
        MIIIIIJJEE
        MIIISIJEEE
        MMMISSJEEE
    """.trimIndent()

    @Test
    fun part1() {
        assertEquals(140, day12.part1(SMALL_EXAMPLE))
        assertEquals(772, day12.part1(INNER_EXAMPLE))
        assertEquals(1930, day12.part1(EXAMPLE))
    }

    val E_EXAMPLE = """
        EEEEE
        EXXXX
        EEEEE
        EXXXX
        EEEEE
    """.trimIndent()

    val INTERLOCKED_EXAMPLE = """
        AAAAAA
        AAABBA
        AAABBA
        ABBAAA
        ABBAAA
        AAAAAA
    """.trimIndent()

    @Test
    fun part2() {
        assertEquals(80, day12.part2(SMALL_EXAMPLE))
        assertEquals(436, day12.part2(INNER_EXAMPLE))
        assertEquals(236, day12.part2(E_EXAMPLE))
        assertEquals(368, day12.part2(INTERLOCKED_EXAMPLE))
        assertEquals(1206, day12.part2(EXAMPLE))
    }
}