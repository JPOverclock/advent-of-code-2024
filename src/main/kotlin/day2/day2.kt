package day2

import kotlin.math.abs

fun part1(input: String): Int {
    val lines = input.split("\n")

    return lines
        .map { line -> line.split("\\s+".toRegex()).map { it.toLong() } }
        .count() { isSafe(it) }
}

fun part2(input: String): Int {
    val lines = input.split("\n")

    return lines
        .map { line -> line.split("\\s+".toRegex()).map { it.toLong() } }
        .count() { isSafe(it) || isSafeWithDampening(it) }
}

private fun isSafe(report: List<Long>): Boolean {
    var i = 1
    val globalTrend = report[i].compareTo(report[0])

    while (i < report.size) {
        val diff = abs(report[i] - report[i - 1])
        val trend = report[i].compareTo(report[i - 1])

        if (diff < 1 || diff > 3 || trend != globalTrend) return false;
        i++
    }

    return true
}

private fun isSafeWithDampening(report: List<Long>): Boolean {
    return report.indices
        .map { report.filterIndexed { i, _ -> i != it } }
        .any { isSafe(it) }
}