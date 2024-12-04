package day4

data class Position(val x: Int, val y: Int) {
    fun add(position: Position): Position {
        return Position(x + position.x, y + position.y)
    }

    fun mul(constant: Int): Position {
        return Position(x * constant, y * constant)
    }
}

fun countOccurrences(input: String, initialCharacterHint: Char, buildPositionValidations: (position: Position) -> List<List<Pair<Char, Position>>>, occurrenceMapper: (num: Int) -> Long): Long {
    // Input is a 2D array of characters
    val rawBoard = input.split("\n").map { it.toCharArray().toTypedArray() }.toTypedArray()

    // We can map every character into a hash table for safe position checking and keep track of the 'X's
    val initialCharacters = mutableListOf<Position>()
    val board: MutableMap<Position, Char> = mutableMapOf()

    rawBoard.forEachIndexed { i, line ->
        line.forEachIndexed { j, item ->
            board.put(Position(j, i), item)

            if (item == initialCharacterHint) {
                initialCharacters.add(Position(j, i))
            }
        }
    }

    fun isValidWord(positions: List<Pair<Char, Position>>): Boolean {
        return positions.all { board[it.second] == it.first }
    }

    // For each starting position, we build the list of validations and count how many succeed
    return initialCharacters.map { buildPositionValidations(it) }
        .sumOf { candidates ->
            val count = candidates.count { c -> isValidWord(c) }
            occurrenceMapper(count)
        }
}

fun part1(input: String): Long {
    val word = "XMAS"

    // Given a valid 'X' position, we can define a function that builds a set of
    // position validations for the remaining letters in all directions
    val directions = listOf(
        Position(1, 0),
        Position(1, 1),
        Position(0, 1),
        Position(-1, 1),
        Position(-1, 0),
        Position(-1, -1),
        Position(0, -1),
        Position(1, -1)
    )

    fun buildPositionValidations(position: Position): List<List<Pair<Char, Position>>> {
        return directions.map { direction ->
            word.mapIndexed { i, char ->
                char to position.add(direction.mul(i))
            }
        }
    }

    return countOccurrences(input, 'X', ::buildPositionValidations) { v -> v.toLong() }
}

fun part2(input: String): Long {
    // Given a valid 'A' position, we can define a function that builds a set of
    // position validations for the remaining letters in all directions
    val validDirections = listOf(
        listOf(Position(-1, -1) to 'M', Position(1, 1) to 'S'),
        listOf(Position(1, 1) to 'M', Position(-1, -1) to 'S'),
        listOf(Position(-1, 1) to 'M', Position(1, -1) to 'S'),
        listOf(Position(1, -1) to 'M', Position(-1, 1) to 'S')
    )

    fun buildPositionValidations(position: Position): List<List<Pair<Char, Position>>> {
        return validDirections.map { it.map { letter -> letter.second to position.add(letter.first) } }
    }

    return countOccurrences(input, 'A', ::buildPositionValidations) { v -> if (v == 2) 1 else 0 }
}