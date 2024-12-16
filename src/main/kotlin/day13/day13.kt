package day13

data class Vector(val x: Long, val y: Long)

data class CraneGame(val aButton: Vector, val bButton: Vector, val prize: Vector) {
    fun compute(): Long {
        /*
        Equations:

         a * Ax + b * Bx = Px
         a * Ay + b * By = Py

         -- First let's solve for a
         a = (Px - (b * Bx)) / Ax

         -- Then we transpose to the second equation
         ((Px - (b * Bx)) / Ax) * Ay + b * By = Py

         -- and we solve the second for B
         (Px - b * Bx) / Ax = (Py - b * By) / Ay

         -- To get rid of the denominators, we multiply by AxAy

         (Px - b * Bx) * Ay = (Py - b * By) Ax
         PxAy - b*BxAy = PyAx - b*ByAx

         -b*BxAy + bByAx = PyAx - PxAy
         b(ByAx - BxAy) = PyAx - PxAy

         ==> b = (PyAx - PxAy) / (ByAx - BxAy)
         */

        val bDenominator = (bButton.y * aButton.x) - (bButton.x * aButton.y)
        val bNumerator = (prize.y * aButton.x) - (prize.x * aButton.y)

        val bDiv = bNumerator / bDenominator
        val bRem = bNumerator % bDenominator

        if (bRem != 0L) {
            // Not divisible... abort
            return -1
        }

        val aNumerator = prize.x - (bDiv * bButton.x)
        val aDenominator = aButton.x

        val aDiv = aNumerator / aDenominator
        val aRem = aNumerator % aDenominator

        if (aRem != 0L) {
            // Not divisible?... abort
            return -1
        }

        return aDiv * 3 + bDiv
    }
}

fun part1(input: String): Long {
    val games = input.split("\n\n").map { parseGame(it) }

    return games.map { it.compute() }.filter { it != -1L }.sum()
}

fun part2(input: String): Long {
    val extra = 10000000000000

    val games = input.split("\n\n")
        .map { parseGame(it) }
        .map { CraneGame(it.aButton, it.bButton, Vector(extra + it.prize.x, extra + it.prize.y)) }

    return games.map { it.compute() }.filter { it != -1L }.sum()
}

private fun parseGame(input: String): CraneGame {
    fun parseButton(line: String): Vector {
        val parts = line
            .substring(10)
            .split(", ")
            .map { it.trim() }
            .map { it.substring(2) }
            .map { it.toLong() }

        return Vector(parts[0], parts[1])
    }

    fun parsePrize(line : String): Vector {
        val parts = line
            .substring(7)
            .split(", ")
            .map { it.trim() }
            .map { it.substring(2) }
            .map { it.toLong() }

        return Vector(parts[0], parts[1])
    }

    val lines = input.split("\n")

    return CraneGame(
        parseButton(lines[0]),
        parseButton(lines[1]),
        parsePrize(lines[2])
    )
}