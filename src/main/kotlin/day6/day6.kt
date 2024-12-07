package day6

data class Position(val i: Int, val j: Int) {
    fun add(position: Position): Position {
        return Position(i + position.i, j + position.j)
    }
}

data class WalkedPosition(val position: Position, val direction: Position)

data class Lab(val initialPosition: Position, val obstacles: Set<Position>, val width: Int, val height: Int) {
    fun isOutsideBounds(position: Position): Boolean {
        return position.i < 0 || position.i >= height || position.j < 0 || position.j >= width
    }
}

fun part1(input: String): Long {
    val lab = parseInput(input)

    return simulate(lab).distinctBy { it.position }.size.toLong()
}

fun part2(input: String): Long {
    val lab = parseInput(input)

    // Get all positions and walked directions
    val walkedPositions = simulate(lab)

    // The idea is to, for each distinct walked position, add an obstacle immediately ahead if there are no obstacles
    // on the position we want to add it in. We then simulate until one of the other walked positions is hit OR if
    // the simulation ends. If a hit is confirmed, we consider the obstacle to result in a loop
    return walkedPositions
        .map { it.position.add(it.direction) }
        .filter { !lab.obstacles.contains(it) && !lab.isOutsideBounds(it) }
        .distinct()
        .count { isSimulationLooping(lab, it) }
        .toLong()
}

private fun parseInput(input: String): Lab {
    val lines = input.split("\n")
    val height = lines.size
    val width = lines[0].length

    val obstacles = mutableSetOf<Position>()
    var guard = Position(0, 0)

    lines.forEachIndexed { i, line ->
        val row = line.toCharArray()

        row.forEachIndexed { j, character ->
            if (character == '#') {
                obstacles.add(Position(i, j))
            } else if (character == '^') {
                guard = Position(i, j)
            }
        }
    }

    return Lab(guard, obstacles, width, height)
}

private fun simulate(lab: Lab): Set<WalkedPosition> {
    var guard = lab.initialPosition
    val directions = listOf(Position(-1, 0), Position(0, 1), Position(1, 0), Position(0, -1))
    var direction = 0

    val walkedPositions = mutableSetOf<WalkedPosition>()

    while (!lab.isOutsideBounds(guard)) {
        walkedPositions.add(WalkedPosition(guard, directions[direction]))

        val next = guard.add(directions[direction])

        if (lab.obstacles.contains(next)) {
            direction = (direction + 1) % 4
        } else {
            guard = next
        }
    }

    return walkedPositions
}

private fun isSimulationLooping(lab: Lab, newObstacle: Position): Boolean {
    var guard = lab.initialPosition
    val obstacles = mutableSetOf<Position>()
    val directions = listOf(Position(-1, 0), Position(0, 1), Position(1, 0), Position(0, -1))
    var direction = 0

    val walkedPositions = mutableSetOf<WalkedPosition>()

    obstacles.addAll(lab.obstacles)
    obstacles.add(newObstacle)

    walkedPositions.add(WalkedPosition(guard, directions[direction]))

    while (true) {
        if (lab.isOutsideBounds(guard)) return false

        val next = guard.add(directions[direction])

        if (obstacles.contains(next)) {
            direction = (direction + 1) % 4
        } else {
            guard = next
        }

        val walkedPosition = WalkedPosition(guard, directions[direction])

        if (walkedPositions.contains(walkedPosition)) {
            return true
        } else {
            walkedPositions.add(walkedPosition)
        }
    }
}