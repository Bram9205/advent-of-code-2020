package nl.bramkohl.adventofcode

class Challenge8 {

    fun part1() {
        val instructions = parseInput()
        try {
            println("Program finished. Accumulator: ${getAccumulatorValue(instructions)}")
        } catch (e: InfiniteLoopException) {
            println("Infinite loop detected. Accumulator: ${e.accumulator}")
        }
    }

    fun part2() {
        val instructions = parseInput()
        instructions.forEach { instruction ->
            if (instruction.operation == "nop" || instruction.operation == "jmp") {
                instruction.operation = if (instruction.operation == "nop") "jmp" else "nop"
                try {
                    println("Accumulator: ${getAccumulatorValue(instructions)}")
                } catch (e: InfiniteLoopException) {
                    //suppress
                } finally {
                    instruction.operation = if (instruction.operation == "nop") "jmp" else "nop"
                }
            }
        }
    }

    private fun getAccumulatorValue(instructions: List<Instruction>): Int {
        var accumulator = 0
        var pointer = 0
        val history = ArrayList<Int>()
        while (pointer >= 0 && pointer < instructions.size) {
            if (history.contains(pointer)) {
                throw InfiniteLoopException(accumulator)
            }
            history.add(pointer)
            when (instructions[pointer].operation) {
                "nop" -> pointer++
                "acc" -> {
                    accumulator += instructions[pointer].argument
                    pointer++
                }
                "jmp" -> pointer += instructions[pointer].argument
            }
        }
        return accumulator
    }

    private fun parseInput(): List<Instruction> {
        val fileContent = javaClass.classLoader.getResource("input8.txt")!!.readText()
        return fileContent.split(System.lineSeparator()).map { line ->
            val split = line.split(" ")
            Instruction(split[0], Integer.parseInt(split[1]))
        }
    }
}

data class Instruction(var operation: String, val argument: Int)

class InfiniteLoopException(val accumulator: Int) : IllegalStateException()

fun main(args: Array<String>) {
    println("Part 1:")
    Challenge8().part1()

    println("Part 2:")
    Challenge8().part2()
}