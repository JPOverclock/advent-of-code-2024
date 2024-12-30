package day17

import kotlin.coroutines.coroutineContext

class Computer(private var a: Int, private var b: Int, private var c: Int, private val program: Array<Int>, private val output: MutableList<Int>, private val onOut: (Computer, Int) -> Boolean = { _, _ -> true }) {
    private var pc:Int = 0
    private var halt = false

    private val comboOperands = arrayOf(
        { 0 },
        { 1 },
        { 2 },
        { 3 },
        { a },
        { b },
        { c },
    )

    fun reset(a: Int, b: Int, c: Int) {
        this.a = a
        this.b = b
        this.c = c
        this.pc = 0
        this.output.clear()
    }

    fun getProgram(): Array<Int> {
        return program
    }

    fun getOutput(): MutableList<Int> {
        return output
    }

    fun run() {
        this.pc = 0

        val opcodes = arrayOf(this::adv, this::bxl, this::bst, this::jnz, this::bxc, this::out, this::bdv, this::cdv)

        while (pc < program.size && !halt) {
            opcodes[program[pc]].invoke()
        }
    }

    private fun literalOperand(): Int = program[pc + 1]

    private fun comboOperand(): Int = comboOperands[program[pc + 1]].invoke()

    private fun adv() {
        a /= (1 shl comboOperand())
        pc += 2
    }

    private fun bxl() {
        b = b xor literalOperand()
        pc += 2
    }

    private fun bst() {
        b = comboOperand() and 0b111
        pc += 2
    }

    private fun jnz() {
        if (a == 0) {
            pc += 2
            return
        }

        pc = literalOperand()
    }

    private fun bxc() {
        b = b xor c
        pc += 2
    }

    private fun out() {
        val outputValue = comboOperand() and 0b111
        halt = halt && !onOut(this, outputValue)
        output.add(comboOperand() and 0b111)
        pc += 2
    }

    private fun bdv() {
        b = a / (1 shl comboOperand())
        pc += 2
    }

    private fun cdv() {
        c = a / (1 shl comboOperand())
        pc += 2
    }
}

fun part1(input: String): String {
    val computer = parseInput(input)

    computer.run()

    return computer.getOutput().joinToString(",")
}

fun part2(input: String): Int {
    val threads = mutableListOf<Thread>()
    val numThreads = 8
    val candidates = mutableListOf<Int>()

    for (i in 0 until numThreads) {
        threads.add(Thread {
            val computer = parseInput(input) { c, value -> c.getProgram()[c.getOutput().size] == value }

            for (j in 0 until Int.MAX_VALUE step numThreads) {
                if (candidates.isNotEmpty() && j > candidates.min()) return@Thread

                computer.reset(j, 0, 0)
                computer.run()

                if (computer.getOutput().toTypedArray().contentEquals(computer.getProgram())) {
                    candidates.add(j)
                    return@Thread
                }
            }
        })
    }

    threads.forEach { it.start() }
    threads.forEach { it.join() }

    return candidates.min()
}

private fun parseInput(input: String, onOut: (Computer, Int) -> Boolean = {_, _ -> true}): Computer {
    val parts = input.split("\n\n")

    // parts[0] contains the register definitions
    val registers = parts[0].split("\n").map { it.substring(12) }.map { it.toInt() }

    // parts[1] contains the program
    val program = parts[1].substring(9).split(",").map { it.toInt() }.toTypedArray()

    return Computer(registers[0], registers[1], registers[2], program, mutableListOf(), onOut)
}
