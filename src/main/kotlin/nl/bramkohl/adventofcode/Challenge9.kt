package nl.bramkohl.adventofcode

import kotlin.system.exitProcess

class Challenge9 {

    fun part1() {
        val numbers: List<Long> = parseInput()
        for (i in 25 until numbers.size) {
            if (!sumExists(numbers.subList(i - 25, i), numbers[i])) {
                println("First number: ${numbers[i]}")
            }
        }
    }

    private fun sumExists(previousNumbers: List<Long>, total: Long): Boolean {
        for (i in previousNumbers.indices) {
            for (j in i + 1 until previousNumbers.size) {
                if ((previousNumbers[i] + previousNumbers[j]) == total) {
                    return true
                }
            }
        }
        return false
    }

    fun part2() {
        val target = 2089807806L
        val numbers: List<Long> = parseInput()
        for (i in numbers.indices) {
            for (j in i + 2 until numbers.size) {
                val sublistSum = numbers.subList(i, j).sum()
                if (sublistSum == target) {
                    println("Min + Max = ${numbers.subList(i, j).minOrNull()!! + numbers.subList(i, j).maxOrNull()!!}.")
                    exitProcess(0)
                }
                if (sublistSum > target) {
                    break
                }
            }
        }
    }

    private fun parseInput(): List<Long> {
        val fileContent = javaClass.classLoader.getResource("input9.txt")!!.readText()
        return fileContent.split(System.lineSeparator()).map { line ->
            line.toLong()
        }
    }
}

fun main(args: Array<String>) {
    println("Part 1:")
    Challenge9().part1()

    println("Part 2:")
    Challenge9().part2()
}