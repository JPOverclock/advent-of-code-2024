package day11

fun part1(input: String): Long {
    // Memoize results per depth. There's likely going to be A LOT of repetition
    val memo = mutableMapOf<Pair<Int, String>, Long>()

    return input.split(" ").sumOf { blink(it, memo, 0, 25) }
}

fun part2(input: String): Long {
    val memo = mutableMapOf<Pair<Int, String>, Long>()

    return input.split(" ").sumOf { blink(it, memo, 0, 75) }
}

fun blink(stone: String, memo: MutableMap<Pair<Int, String>, Long>, level: Int, maxLevel: Int): Long {
    if (level == maxLevel) return 1

    if (memo.containsKey(level to stone)) {
        return memo[level to stone]!!
    }

    val result: Long

    if (stone == "0") return blink("1", memo, level + 1, maxLevel)

    if (stone.length % 2 == 0) {
        val first = stone.substring(0, stone.length / 2)
        val second = stone.substring(stone.length / 2).trimStart('0').ifEmpty { "0" }

        result = blink(first, memo, level + 1, maxLevel) + blink(second, memo, level + 1, maxLevel)
    } else {
        result = blink((stone.toLong() * 2024).toString(), memo, level + 1, maxLevel)
    }


    memo[level to stone] = result
    return result
}