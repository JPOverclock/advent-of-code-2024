package day7

fun part1(input: String): Long {
    val operations = listOf(
        { a: Long, b: Long -> a + b },
        { a: Long, b: Long -> a * b },
    )

    return parseInput(input)
        .filter { hasSolution(it.first, it.second, operations) }
        .sumOf { it.first }
}

fun part2(input: String): Long {
    val operations = listOf(
        { a: Long, b: Long -> a + b },
        { a: Long, b: Long -> a * b },
        { a: Long, b: Long -> "$a$b".toLong() },
    )

    return parseInput(input)
        .filter { hasSolution(it.first, it.second, operations) }
        .sumOf { it.first }
}

private fun parseInput(input: String): List<Pair<Long, List<Long>>> {
    return input.split("\n")
        .map { it.split(":") }
        .map { it[0].trim().toLong() to it[1].trim().split(" ").map { i -> i.toLong() } }
}

private fun hasSolution(result: Long, values: List<Long>, operations: List<(Long, Long) -> Long>): Boolean {
    val stack = mutableListOf<Long>()

    stack.addAll(values)
    var workingStack = listOf(stack.removeFirst())

    while (stack.isNotEmpty()) {
        val operand = stack.removeFirst()

        workingStack = workingStack
            .flatMap { v -> operations.map { it(v, operand) } }
            .filter { it <= result }
    }

    return workingStack.any { it == result }
}
