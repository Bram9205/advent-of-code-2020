package nl.bramkohl.adventofcode

class Challenge10 {

    fun part1() {
        var numbers: List<Long> = parseInput().sorted()
        numbers = listOf(0L) + numbers + listOf(numbers.last() + 3)
        var ones = 0
        var threes = 0
        for (i in 1 until numbers.size) {
            if (numbers[i] - numbers[i - 1] == 1L) {
                ones++
            } else if (numbers[i] - numbers[i - 1] == 3L) {
                threes++
            }
        }
        println("Ones: $ones, Threes: $threes, multiplied: ${ones * threes}")
    }

    fun part2() {
        val adapterOutputs: List<Long> = listOf(0L) + parseInput().sorted()
        println("Tracks: ${possiblePaths(0, adapterOutputs)}")
    }

    private val memory: HashMap<Long, Long> = HashMap()

    private fun possiblePaths(current: Long, adapterOutputs: List<Long>): Long {
        if (current == adapterOutputs.last()) return 1
        if (memory.containsKey(current)) return memory[current]!!

        val first = if (adapterOutputs.contains(current + 1)) possiblePaths(current + 1, adapterOutputs) else 0
        val second = if (adapterOutputs.contains(current + 2)) possiblePaths(current + 2, adapterOutputs) else 0
        val third = if (adapterOutputs.contains(current + 3)) possiblePaths(current + 3, adapterOutputs) else 0
        val result = first + second + third

        memory[current] = result
        return result
    }


    private fun parseInput(): List<Long> {
        val fileContent = javaClass.classLoader.getResource("input10.txt")!!.readText()
        return fileContent.split(System.lineSeparator()).map { line ->
            line.toLong()
        }
    }
}

fun main() {
    println("Part 1:")
    Challenge10().part1()

    println("Part 2:")
    Challenge10().part2()
}