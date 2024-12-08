package day8

data class Position(val x: Int, val y: Int) {
    fun minus(position: Position): Position {
        return Position(x - position.x, y - position.y)
    }

    fun add(position: Position): Position {
        return Position(x + position.x, y + position.y)
    }
}

data class Antenna(val type: Char, val position: Position) {

    fun antinodes(other: Antenna): List<Position> {
        val thisToOther = position.minus(other.position)
        val otherToThis = other.position.minus(this.position)

        return listOf(
            this.position.minus(otherToThis),
            other.position.minus(thisToOther)
        )
    }
}

data class Rooftop(val height: Int, val width: Int, val antennae: Map<Char, List<Antenna>>)

fun part1(input: String): Long {
    val rooftop = parseInput(input)

    val antinodes = rooftop.antennae.mapValues { types ->
        val sameTypeAntennae = types.value
        val antinodes = mutableListOf<Position>()

        for (i in 0 until sameTypeAntennae.size) {
            for (j in i + 1 until sameTypeAntennae.size) {
                sameTypeAntennae[i].antinodes(sameTypeAntennae[j])
                    .filter { it.x >= 0 && it.x < rooftop.width && it.y >= 0 && it.y < rooftop.height }
                    .forEach { antinodes += it }
            }
        }

        antinodes
    }

    return antinodes.flatMap { it.value }.distinct().count().toLong()
}

fun part2(input: String): Long {
    val rooftop = parseInput(input)

    val antinodes = rooftop.antennae.mapValues { types ->
        val sameTypeAntennae = types.value
        val antinodes = mutableListOf<Position>()

        for (i in 0 until sameTypeAntennae.size) {
            val first = sameTypeAntennae[i]

            for (j in i + 1 until sameTypeAntennae.size) {
                val second = sameTypeAntennae[j]

                val nodes = mutableListOf<Position>()

                // ... And other antinodes along the lines of the antennae until they run out of the rooftop
                val forward = second.position.minus(first.position)
                val backwards = first.position.minus(second.position)

                var start = first.position

                while (start.x >= 0 && start.x < rooftop.width && start.y >= 0 && start.y < rooftop.height) {
                    nodes.add(start)
                    start = start.add(forward)
                }

                start = second.position

                while (start.x >= 0 && start.x < rooftop.width && start.y >= 0 && start.y < rooftop.height) {
                    nodes.add(start)
                    start = start.add(backwards)
                }

                antinodes.addAll(nodes.distinct())
            }
        }

        antinodes
    }

    return antinodes.flatMap { it.value }.distinct().count().toLong()
}

private fun parseInput(input: String): Rooftop {
    val lines = input.split("\n")

    val height = lines.size
    val width = lines[0].length

    val antennae = lines
        .flatMapIndexed { y, line ->
            line.toCharArray().mapIndexed { x, type ->
                Antenna(type, Position(x, y))
            }
        }
        .filter { it.type != '.' }
        .groupBy { it.type }

    return Rooftop(height, width, antennae)
}