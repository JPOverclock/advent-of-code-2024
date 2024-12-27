package day16

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day16KtTest {

    val EXAMPLE_1 = """
        ###############
        #.......#....E#
        #.#.###.#.###.#
        #.....#.#...#.#
        #.###.#####.#.#
        #.#.#.......#.#
        #.#.#####.###.#
        #...........#.#
        ###.#.#####.#.#
        #...#.....#.#.#
        #.#.#.###.#.#.#
        #.....#...#.#.#
        #.###.#.#.#.#.#
        #S..#.....#...#
        ###############
    """.trimIndent()

    val EXAMPLE_2 = """
        #################
        #...#...#...#..E#
        #.#.#.#.#.#.#.#.#
        #.#.#.#...#...#.#
        #.#.#.#.###.#.#.#
        #...#.#.#.....#.#
        #.#.#.#.#.#####.#
        #.#...#.#.#.....#
        #.#.#####.#.###.#
        #.#.#.......#...#
        #.#.###.#####.###
        #.#.#...#.....#.#
        #.#.#.#####.###.#
        #.#.#.........#.#
        #.#.#.#########.#
        #S#.............#
        #################
    """.trimIndent()

    @Test
    fun part1Example1() {
        assertEquals(7036, part1(EXAMPLE_1))
    }

    @Test
    fun part1Example2() {
        assertEquals(11048, part1(EXAMPLE_2))
    }

    @Test
    fun part2Example1() {
        assertEquals(45, part2(EXAMPLE_1))
    }

    @Test
    fun part2Example2() {
        assertEquals(64, part2(EXAMPLE_2))
    }
}