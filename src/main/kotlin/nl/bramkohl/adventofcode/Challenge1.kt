package nl.bramkohl.adventofcode

class Challenge1 {

    fun part2() {
        val numbers = parseInput()
        for (i in numbers.indices) {
            for (j in i+1 until numbers.size) {
                for (k in j+1 until numbers.size)
                if (numbers[i] + numbers[j] + numbers[k] == 2020) {
                    println("${numbers[i]} + ${numbers[j]}  + ${numbers[k]} = 2020. " +
                            "${numbers[i]}x${numbers[j]}x${numbers[k]} = ${numbers[i]*numbers[j]*numbers[k]}")
                }
            }
        }
    }

    private fun parseInput(): List<Int> {
        val fileContent = javaClass.classLoader.getResource("input1.txt")!!.readText()
        return fileContent.split(System.lineSeparator()).map { lineString ->
            Integer.parseInt(lineString)
        }
    }
}

fun main(args: Array<String>) {
    Challenge1().part2()
}