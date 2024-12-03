package day3

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
class Day3KtTest {

    val EXAMPLE = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"
    val EXAMPLE_2 = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"

    @Test
    fun part1() {
        assertEquals(161, day3.part1(EXAMPLE))
    }

    @Test
    fun part2() {
        assertEquals(48, day3.part2(EXAMPLE_2))
    }
}