package day10

data class Position(val x: Int, val y: Int) {
    fun add(other: Position): Position {
        return Position(x + other.x, y + other.y)
    }
}

class TrailMap(val width: Int, val height: Int, val heights: Map<Position, Int>) {
    fun trailHeads(): List<Position> {
        return heights.filter { it.value == 0 }.map { it.key }
    }

    fun heightAt(position: Position): Int {
        return heights.getOrDefault(position, -1)
    }

    fun neighbours(position: Position): List<Position> {
        if (!isPositionInMap(position)) return listOf()

        return listOf(Position(1, 0), Position(-1, 0), Position(0, 1), Position(0, -1))
            .map { position.add(it) }
            .filter { isPositionInMap(it) }
    }

    private fun isPositionInMap(position: Position): Boolean {
        return position.x >= 0 && position.x < width && position.y >= 0 && position.y < height
    }
}

fun part1(input: String): Long {
    val trailMap = parseInput(input)

    return trailMap.trailHeads()
        .flatMap { searchForMax(it, trailMap) }
        .size
        .toLong()
}

fun part2(input: String): Long {
    val trailMap = parseInput(input)

    return trailMap.trailHeads()
        .flatMap { searchForMax(it, trailMap, true) }
        .size
        .toLong()
}

private fun parseInput(input: String): TrailMap {
    val lines = input.split("\n")
    val nodes = mutableMapOf<Position, Int>()

    lines.forEachIndexed { y, line ->
        val columns = line.toCharArray()

        columns.forEachIndexed { x, height ->
            nodes[Position(x, y)] = height - '0'
        }
    }

    return TrailMap(lines[0].length, lines.size, nodes)
}

private fun searchForMax(trailHead: Position, trailMap: TrailMap, ignoreVisited: Boolean = false): List<Position> {
    val visited = mutableListOf<Position>()
    val frontier = mutableListOf(trailHead)
    val finalNodes = mutableListOf<Position>()

    while (frontier.isNotEmpty()) {
        val position = frontier.removeFirst()

        if (visited.contains(position)) continue

        if (trailMap.heightAt(position) == 9) {
            // Top reached
            finalNodes.add(position)
        } else {
            // Expand
            trailMap
                .neighbours(position)
                .filter { !visited.contains(it) }
                .map { it to trailMap.heightAt(it) }
                .filter { it.second == (trailMap.heightAt(position) + 1) }
                .forEach { (nextPosition, _) -> frontier.add(nextPosition) }
        }

        if (!ignoreVisited) {
            visited.add(position)
        }
    }

    return finalNodes
}