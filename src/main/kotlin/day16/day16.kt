package day16

data class Vector(val x: Int, val y: Int) {
    operator fun plus(v: Vector) = Vector(x + v.x, y + v.y)

    fun rotateLeft(): Vector {
        return Vector(y, -x)
    }

    fun rotateRight(): Vector {
        return Vector(-y, x)
    }
}

data class VisitedPosition(val position: Vector, val direction: Vector)

data class Maze(val start: Vector, val end: Vector, val walls: Set<Vector>, val nodes: Set<Vector>) {
    fun options(node: VisitedPosition): List<Pair<VisitedPosition, Long>> {
        return listOf(
            VisitedPosition(node.position + node.direction, node.direction, ) to 1L,
            VisitedPosition(node.position + node.direction.rotateLeft(), node.direction.rotateLeft()) to 1001L,
            VisitedPosition(node.position + node.direction.rotateRight(), node.direction.rotateRight()) to 1001L
        ).filterNot { walls.contains(it.first.position) }
    }
}

fun part1(input: String): Long {
    val maze = parseInput(input)
    val directions = listOf(
        Vector(1, 0),
        Vector(0, 1),
        Vector(-1, 0),
        Vector(0, -1)
    )

    // Let's dijkstra it :)
    val costs = mutableMapOf<VisitedPosition, Long>()
    val parents = mutableMapOf<VisitedPosition, MutableList<VisitedPosition>>()

    costs[VisitedPosition(maze.start, Vector(1, 0))] = 0


    val processed = mutableSetOf<VisitedPosition>()

    fun lowestCostNode(): VisitedPosition? {
        return costs.keys.filterNot { it in processed }.minByOrNull { costs[it]!! }
    }

    var node = lowestCostNode()

    while (node != null) {
        val cost = costs[node]!!

        maze.options(node).forEach { (option, score) ->
            val newCost = cost + score

            if (costs.getOrDefault(option, Long.MAX_VALUE) > newCost) {
                costs[option] = newCost
                parents[option] = mutableListOf(node!!)
            } else if (costs.getOrDefault(option, Long.MAX_VALUE) == newCost) {
                val list = parents.getOrDefault(option, mutableListOf())
                list.add(node!!)

                parents[option] = list
            }
        }

        processed.add(node)
        node = lowestCostNode()
    }

    return directions.map { VisitedPosition(maze.end, it) }.minOf { costs.getOrDefault(it, Long.MAX_VALUE) }
}

fun part2(input: String): Long {
    val maze = parseInput(input)
    val directions = listOf(
        Vector(1, 0),
        Vector(0, 1),
        Vector(-1, 0),
        Vector(0, -1)
    )

    // Let's dijkstra it :)
    val costs = mutableMapOf<VisitedPosition, Long>()
    val parents = mutableMapOf<VisitedPosition, MutableList<VisitedPosition>>()

    costs[VisitedPosition(maze.start, Vector(1, 0))] = 0


    val processed = mutableSetOf<VisitedPosition>()

    fun lowestCostNode(): VisitedPosition? {
        return costs.keys.filterNot { it in processed }.minByOrNull { costs[it]!! }
    }

    var node = lowestCostNode()

    while (node != null) {
        val cost = costs[node]!!

        maze.options(node).forEach { (option, score) ->
            val newCost = cost + score

            if (costs.getOrDefault(option, Long.MAX_VALUE) > newCost) {
                costs[option] = newCost
                parents[option] = mutableListOf(node!!)
            } else if (costs.getOrDefault(option, Long.MAX_VALUE) == newCost) {
                val list = parents.getOrDefault(option, mutableListOf())
                list.add(node!!)

                parents[option] = list
            }
        }

        processed.add(node)
        node = lowestCostNode()
    }

    // Do a back walk
    val path = mutableSetOf<VisitedPosition>()

    val endNode = directions.map { VisitedPosition(maze.end, it) }.minBy { costs.getOrDefault(it, Long.MAX_VALUE) }

    val frontier = mutableListOf(endNode);

    while (frontier.isNotEmpty()) {
        val head = frontier.removeFirst()

        // Expand parents
        val next = parents.getOrDefault(head, listOf())

        path.add(head)
        frontier.addAll(next)
    }

    return path.distinctBy { it.position }.size.toLong()
}

private fun parseInput(input: String): Maze {
    val walls = mutableSetOf<Vector>()
    val nodes = mutableSetOf<Vector>()
    var start = Vector(0, 0)
    var end = Vector(0, 0)

    input.lines().forEachIndexed { y, line ->
        line.toCharArray().forEachIndexed { x, c ->
            when (c) {
                '#' -> walls.add(Vector(x, y))
                'S' -> start = Vector(x, y)
                'E' -> end = Vector(x, y)
                else -> nodes.add(Vector(x, y))
            }
        }
    }

    return Maze(start, end, walls, nodes)
}