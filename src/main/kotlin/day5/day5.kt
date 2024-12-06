package day5

fun part1(input: String): Long {
    val (rules, lists) = parseInput(input)

    return lists
        .filter { violations(it, rules).isEmpty() }
        .sumOf { it[it.size / 2] }
}

fun part2(input: String): Long {
    val (rules, lists) = parseInput(input)

    return lists
        .filter { violations(it, rules).isNotEmpty() }
        .map { fixList(it, rules) }
        .sumOf { it[it.size / 2] }
}

private fun parseInput(input: String): Pair<List<Pair<Long, Long>>, List<List<Long>>> {
    val parts = input.split("\n\n")

    val rules = parts[0].split("\n").map { it.split("|") }.map { it[0].toLong() to it[1].toLong() }
    val lists = parts[1].split("\n").map { it.split(",").map { i -> i.toLong() } }

    return Pair(rules, lists)
}

private fun violations(list: List<Long>, rules: List<Pair<Long, Long>>): List<Pair<Long, Long>> {
    return list.flatMap { item ->
        rules.filter { it.first == item && it.second in list && list.indexOf(it.second) < list.indexOf(item) }
    }
}

private fun fixList(list: List<Long>, rules: List<Pair<Long, Long>>): List<Long> {
    val fixedList = mutableListOf<Long>()
    fixedList.addAll(list)

    while (true) {
        val violations = violations(fixedList, rules)

        if (violations.isEmpty()) {
            return fixedList
        }

        // Pick the first violation and fix it
        val violation = violations[0]

        val indexOfFirst = fixedList.indexOf(violation.first)
        val indexOfSecond = fixedList.indexOf(violation.second)

        fixedList[indexOfFirst] = violation.second
        fixedList[indexOfSecond] = violation.first
    }
}
