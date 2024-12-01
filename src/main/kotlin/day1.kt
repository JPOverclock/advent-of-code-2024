import kotlin.math.abs

private fun parseInput(input: String): Pair<MutableList<Long>, MutableList<Long>> {
    val left = mutableListOf<Long>()
    val right = mutableListOf<Long>()

    input.split("\n")
        .map { it.split("\\s+".toRegex()) }
        .map { it[0].toLong() to it[1].toLong() }
        .forEach { (a, b) -> left.add(a); right.add(b) }

    return left to right
}

fun part1(input: String): Long {
    val (left, right) = parseInput(input)

    left.sort()
    right.sort()

    return left.indices.sumOf { abs(left[it] - right[it]) }
}

fun part2(input: String): Long {
    val (left, right) = parseInput(input)

    val frequencies = right.groupingBy { it }.eachCount()

    return left.sumOf { frequencies.getOrDefault(it, 0) * it }
}