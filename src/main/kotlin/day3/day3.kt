package day3

fun part1(input: String): Long {
    val regex = "mul\\(\\d{1,3},\\d{1,3}\\)".toRegex()
    return regex.findAll(input).map { it.value }.map { eval(it) }.sumOf { it }
}

fun part2(input: String): Long {
    val regex = "do\\(\\)|don't\\(\\)".toRegex()
    val matches = regex.findAll(input);

    val changePoints = mutableListOf(true to 0);

    matches.forEach { match ->
        if (match.value == "do()") {
            changePoints.add(true to match.range.last)
        } else {
            // Ignore
            changePoints.add(false to match.range.last)
        }
    }

    changePoints.add(false to input.length)

    // Run over pairs of changes and evaluate
    return changePoints.windowed(2).sumOf { window ->
        if (window.first().first) {
            part1(input.substring(window.first().second, window.last().second));
        } else {
            0
        }
    }
}

fun eval(input: String): Long {
    return input
        .substringAfter("(")
        .substringBefore(")")
        .split(",")
        .map { it.toLong() }
        .reduce { acc, l -> acc * l }
}