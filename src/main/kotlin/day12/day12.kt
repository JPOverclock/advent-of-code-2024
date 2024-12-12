package day12

data class Position(val x: Int, val y: Int) {
    fun add(other: Position): Position {
        return Position(x + other.x, y + other.y)
    }
}

data class Edge(val a: Position, val b: Position, val direction: Position)

data class Plot(val type: Char, val position: Position, var regionId: Int) {
    fun neighbours(plots: Map<Position, Plot>): List<Plot> {
        val directions = listOf(Position(1, 0), Position(0, 1), Position(0, -1), Position(-1, 0))
        return directions.map { position.add(it) }.mapNotNull { plots[it] }
    }

    /**
     * Edge map for an example position:
     *
     * A---B   A-B = (3,7)-(4,7)
     * |3,7|   C-D = (3,8)-(4,8)
     * C---D   A-C = (3,7)-(3,8)
     *         B-D = (4,7)-(4,8)
     */
    fun edges(plots: Map<Position, Plot>): List<Edge> {
        val directions = listOf(Position(1, 0), Position(0, 1), Position(0, -1), Position(-1, 0))

        return directions
            .map { it to position.add(it) }
            .map { it.first to plots[it.second] }
            .filter { it.second == null || it.second?.regionId != regionId }
            .map { it.first }
            .map { direction ->
                val edge = if (direction == Position(0, -1)) {
                    // Above, edge is A-B
                    Edge(Position(position.x, position.y), Position(position.x + 1, position.y), direction)
                } else if (direction == Position(0, 1)) {
                    // Below, edge is C-D
                    Edge(Position(position.x, position.y + 1), Position(position.x + 1, position.y + 1), direction)
                } else if (direction == Position(-1, 0)) {
                    // Before, edge ia A-C
                    Edge(Position(position.x, position.y), Position(position.x, position.y + 1), direction)
                } else {
                    // After, edge is B-D
                    Edge(Position(position.x + 1, position.y), Position(position.x + 1, position.y + 1), direction)
                }

                edge
            }
    }

    fun expand(plots: Map<Position, Plot>): List<Plot> {
        val visited = mutableListOf<Plot>()
        val frontier = mutableListOf(this)

        while (frontier.isNotEmpty()) {
            val pivot = frontier.removeFirst()

            if (visited.contains(pivot)) continue

            frontier.addAll(pivot.neighbours(plots).filter { it.type == pivot.type }.filter { !visited.contains(it) })
            visited.add(pivot)
        }

        return visited
    }
}

fun part1(input: String): Long {
    val plots = parseInput(input)

    val regions = plots.values.groupBy { it.regionId }

    return regions.values
        .sumOf { regionPlots ->
            val perimeter = regionPlots.sumOf { 4 - it.neighbours(plots).count { p -> p.regionId == it.regionId }.toLong() }
            val area = regionPlots.size

            perimeter * area
        }
}

fun part2(input: String): Long {
    val plots = parseInput(input)

    val regions = plots.values.groupBy { it.regionId }

    return regions.values.sumOf { regionPlots ->
        val sides = regionPlots
            .flatMap { it.edges(plots) }
            .groupBy { it.direction }
            .flatMap { combineEdges(it.value) }
            .count()

        sides * regionPlots.size.toLong()
    }
}

private fun parseInput(input: String): Map<Position, Plot> {
    val plots = mutableMapOf<Position, Plot>()
    var regionId = 1

    val lines = input.split("\n")
    lines.forEachIndexed { y, line ->
        line.toCharArray().forEachIndexed { x, c ->
            val position = Position(x, y)

            plots[position] = Plot(c, position, 0)
        }
    }

    // Consolidate plots
    while (true) {
        // Find a plot that does not have a region
        val head = plots.values.firstOrNull { it.regionId == 0 }

        if (head == null) {
            // all plots have regions assigned; end
            break
        }

        head.expand(plots).forEach { it.regionId = regionId }
        regionId++
    }

    return plots
}

private fun combineEdges(edges: List<Edge>): List<Edge> {
    // Start with a list of all edges to combine
    val toCombine = mutableListOf<Edge>()
    toCombine.addAll(edges)

    val combined = mutableListOf<Edge>()

    while (toCombine.isNotEmpty()) {
        // Pick up the first edge and try to combine it
        val head = toCombine.removeFirst()

        if (toCombine.any { it.a == head.b || it.b == head.a }) {
            // Can be combined
            var a = head.a
            var b = head.b

            // Find something before and append it
            toCombine.find { it.b == a }?.let { r ->
                a = r.a
                toCombine.remove(r)
            }

            // Find something after and append it
            toCombine.find { it.a == b }?.let { r ->
                b = r.b
                toCombine.remove(r)
            }

            toCombine.addFirst(Edge(a, b, head.direction))

        } else {
            // Fully combined, just add it to the combined list
            combined.add(head)
        }
    }

    return combined
}