package day14

data class Vector(val x: Long, val y: Long) {
    fun add(vector: Vector): Vector {
        return Vector(x + vector.x, y + vector.y)
    }

    fun multiply(k: Long): Vector {
        return Vector(k * x, k * y)
    }
}

data class Robot(var position: Vector, val velocity: Vector) {
    fun simulate(steps: Long) {
        position = position.add(velocity.multiply(steps))
    }

    fun clampTo(width: Long, height: Long) {
        val clampedX = position.x % width
        val clampedY = position.y % height

        val x = if (clampedX < 0) width + clampedX else clampedX
        val y = if (clampedY < 0) height + clampedY else clampedY

        position = Vector(x, y)
    }
}

fun part1(input: String, width: Long, height: Long): Long {
    val robots = parseInput(input)

    // Simulate
    robots.forEach {
        it.simulate(100)
        it.clampTo(width, height)
    }

    // Count per quadrant
    val quadrants = listOf(
        Vector(0, 0) to Vector(width / 2, height / 2),
        Vector(width / 2 + 1, 0) to Vector(width, height / 2),
        Vector(0, height / 2 + 1) to Vector(width / 2, height),
        Vector(width / 2 + 1, height / 2 + 1) to Vector(width, height)
    )

    return quadrants.map { (a,b) ->
        robots.filter { robot ->
            robot.position.x >= a.x && robot.position.x < b.x
                    && robot.position.y >= a.y && robot.position.y < b.y
        }
    }
        .map { it.count().toLong() }
        .reduce { acc, l -> acc * l }
}

fun part2(input: String, width: Long, height: Long): Long {
    val robots = parseInput(input)

    for (i in 0 until width * height) {

        val positions = robots.map { it.position }

        val kernel = listOf(Vector(-1, 0), Vector(-1, -1), Vector(0, -1), Vector(1, -1), Vector(1, 0), Vector(1, 1), Vector(0, 1), Vector(-1, 1))

        val neighbours = positions.map { position ->
            val neighbours = kernel.map { position.add(it) }.count { positions.contains(it) }
            neighbours
        }

        val neighbourCounts = neighbours.groupingBy { it }.eachCount()

        if (neighbourCounts.getOrDefault(8, 0) >= 6) return i

        robots.forEach {
            it.simulate(1)
            it.clampTo(width, height)
        }
    }

    return -1
}

private fun parseInput(input: String): List<Robot> {
    fun parseVector(input: String): Vector {
        val components = input.split(",")
        return Vector(components[0].toLong(), components[1].toLong())
    }

    fun parseRobot(line: String): Robot {
        val parts = line.split(" ")
        return Robot(
            parseVector(parts[0].substring(2)),
            parseVector(parts[1].substring(2))
        )
    }

    return input.lines().map { parseRobot(it) }
}

private fun drawRobots(robots: List<Robot>, width: Long, height: Long) {
    // Draw simulation
    for (i in 0 until height) {
        for (j in 0 until width) {
            val position = Vector(j, i)

            val count = robots.count { it.position == position }

            if (count == 0) {
                print(".")
            } else {
                print(count)
            }
        }

        println()
    }
}
