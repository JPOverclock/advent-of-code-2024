package day9

fun part1(input: String): Long {
    val layout = input.toCharArray().map { it - '0' }
    val diskSize = layout.sum()

    // Solved naively by fully expanding the filesystem, moving the blocks and calculating the checksum
    val disk = Array(diskSize, { -1 })

    // Expand filesystem
    var id = 0
    var ptr = 0

    layout.forEachIndexed { index, i ->
        val value = if (index % 2 == 0) id++ else -1

        for (j in 0 until i) {
            disk[ptr++] = value
        }
    }

    // Compact
    var start = 0
    var end = disk.size - 1

    while (true) {
        // Find first free block and first non-free block
        while (disk[start] != -1) ++start
        while(disk[end] == -1) --end

        if (start >= end) break

        disk[start] = disk[end]
        disk[end] = -1
    }

    // Calculate checksum
    return disk.mapIndexed { index, i -> index.toLong() * i.toLong() }.filter { it >= 0 }.sum()
}

data class Allocation(val start: Int, val end: Int) {
    fun size(): Int {
        return end - start
    }

    fun fits(other: Allocation): Boolean {
        return other.size() <= size()
    }

    fun fit(other: Allocation): Pair<Allocation, Allocation> {
        if (other.size() == size()) return this to Allocation(0, 0)
        return Allocation(start, start + other.size()) to Allocation(start + other.size(), start + size())
    }

    fun isToTheLeftOf(other: Allocation): Boolean {
        return end <= other.start
    }
}

data class File(val id: Int, var blocks: Allocation) {
    fun checksum(): Long {
        return (blocks.start until blocks.end).sumOf { it * id.toLong() }
    }
}

fun part2(input: String): Long {
    val layout = input.toCharArray().map { it - '0' }

    // Now, instead of allocating the disk, let's just build a couple of lists for both the files and the free space
    val files = mutableListOf<File>()
    val emptySpace = mutableListOf<Allocation>()

    var id = 0
    var block = 0

    layout.forEachIndexed { index, item ->
        if (index % 2 == 0) {
            // File as on even positions
            files.add(File(id++, Allocation(block, block + item)))
        } else {
            // Empty space on odd
            emptySpace.add(Allocation(block, block + item))
        }

        block += item
    }

    // And now, we start from the last file and try to find empty spaces
    files.reversed().forEach { file ->
        // Find a suitable empty space
        val space = emptySpace.sortedBy { it.start }.firstOrNull { it.isToTheLeftOf(file.blocks) && it.fits(file.blocks) }

        if (space != null) {
            // Change allocation
            val (fileBlocks, freeBlocks) = space.fit(file.blocks)

            file.blocks = fileBlocks

            emptySpace.remove(space)

            if (freeBlocks.size() > 0) {
                emptySpace.add(freeBlocks)
            }
        }
    }

    // Finally, we just calculate the checksum of the modified files :)
    return files.sumOf { it.checksum() }
}