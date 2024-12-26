package day15

data class Vector(val x: Int, val y: Int) {
    fun add(vector: Vector): Vector {
        return Vector(x + vector.x, y + vector.y)
    }
}

data class Warehouse(
    val walls: List<Vector>,
    val boxes: List<Box>,
    val width: Int,
    val height: Int,
    var robotPosition: Vector,
    val movements: List<Vector>) {

    fun simulate() {
        fun canPushBox(box: Box, direction: Vector): Boolean {
            val nextPositions = box.nextPositions(direction)

            if (nextPositions.any { walls.contains(it) }) {
                // There's a wall in the next positions; Cannot do anything
                return false
            }

            val possibleBoxes = nextPositions.mapNotNull { boxes.find { b -> b.isAt(it) } }.distinct()

            return if (possibleBoxes.isEmpty()) {
                true
            } else {
                possibleBoxes.all { canPushBox(it, direction) }
            }
        }

        fun pushBox(box: Box, direction: Vector) {
            if (canPushBox(box, direction)) {
                // Push all child boxes
                box.nextPositions(direction)
                    .mapNotNull { boxes.find { b -> b.isAt(it) } }
                    .distinct()
                    .forEach { pushBox(it, direction) }

                // Push itself
                box.push(direction)
            }
        }

        movements.forEach { direction ->
            val nextPosition = robotPosition.add(direction)

            if (walls.contains(nextPosition)) return@forEach

            if (boxes.none { it.isAt(nextPosition) }) {
                robotPosition = nextPosition
                return@forEach
            }

            val box = boxes.find { it.isAt(nextPosition) }!!

            // Try to push the box
            pushBox(box, direction)

            if (boxes.none { it.isAt(nextPosition) }) {
                // Push was successful! Move the robot to the new position
                robotPosition = nextPosition
            }
        }
    }

    fun score(): Long {
        return boxes.sumOf { it.position().y.toLong() * 100 + it.position().x }
    }
}

interface Box {
    fun nextPositions(direction: Vector): List<Vector>
    fun isAt(position: Vector): Boolean
    fun position(): Vector
    fun push(direction: Vector)
}

class SimpleBox(private var position: Vector) : Box {
    override fun nextPositions(direction: Vector): List<Vector> {
        return listOf(position.add(direction))
    }

    override fun isAt(position: Vector): Boolean {
        return position == this.position
    }

    override fun position(): Vector {
        return position
    }

    override fun push(direction: Vector) {
        position = position.add(direction)
    }
}

class DoubleWideBox(private var position: Vector) : Box {
    private var side = position.add(Vector(1, 0))

    override fun nextPositions(direction: Vector): List<Vector> {
        return when (direction) {
            Vector(1, 0) -> listOf(side.add(direction))
            Vector(-1, 0) -> listOf(position.add(direction))
            else -> listOf(position.add(direction), side.add(direction))
        }
    }

    override fun isAt(position: Vector): Boolean {
        return position == this.position || position == side
    }

    override fun position(): Vector {
        return position
    }

    override fun push(direction: Vector) {
        position = position.add(direction)
        side = side.add(direction)
    }
}

fun part1(input: String): Long {
    val warehouse = parseInput(input, false)

    warehouse.simulate()

    return warehouse.score()
}

fun part2(input: String): Long {
    val warehouse = parseInput(input, true)

    warehouse.simulate()

    return warehouse.score()
}

private fun parseInput(input: String, wide: Boolean): Warehouse {
    val parts = input.split("\n\n")

    // Build map out of lines on parts[0]
    val mapLines = parts[0].lines()
    val height = mapLines.size
    val width = mapLines[0].length * (if (wide) 2  else 1)

    var startingPosition = Vector(-1, -1)

    val boxes = mutableListOf<Box>()
    val walls = mutableListOf<Vector>()

    mapLines.forEachIndexed { y, line ->
        line.toCharArray().forEachIndexed { x, c ->
            val realX = if (wide) x * 2 else x
            val position = Vector(realX, y)

            when (c) {
                '@' -> startingPosition = position
                '#' -> {
                    walls.add(position)
                    if (wide) walls.add(position.add(Vector(1, 0)))
                }
                'O' -> {
                    boxes.add(if (wide) DoubleWideBox(position) else SimpleBox(position))
                }
            }
        }
    }

    // ... then just movements out of the parts[1]
    fun direction(c: Char): Vector {
        return when (c) {
            '<' -> Vector(-1, 0)
            '>' -> Vector(1, 0)
            'v' -> Vector(0, 1)
            else -> Vector(0, -1)
        }
    }

    val movements = parts[1].replace("\n", "").toCharArray().map { direction(it) }.toList()

    return Warehouse(walls, boxes, width, height, startingPosition, movements)
}