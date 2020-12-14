package nl.bramkohl.adventofcode

class Challenge5 {

    fun part1() {
        val seats = parseInput()
        val max = seats.map { seat -> seat.row * 8 + seat.column }.maxOrNull()
        println("max: $max")
    }

    fun part2() {
        val seats = parseInput()
        var last = 0
        seats.map { seat -> seat.row * 8 + seat.column }.sorted().forEach { number: Int ->
            if (last != 0 && (number - last) > 1) {
                println("Missing: ${number - 1}")
            }
            last = number
        }
    }

    private fun parseInput(): List<Seat> {
        val fileContent = javaClass.classLoader.getResource("input5.txt")!!.readText()
        return fileContent.split(System.lineSeparator()).map { binary ->
            toSeat(binary)
        }
    }

    private fun toSeat(binary: String): Seat {
        val row = Integer.parseInt(binary.take(7).replace("F", "0").replace("B", "1"), 2)
        val column = Integer.parseInt(binary.takeLast(3).replace("L", "0").replace("R", "1"), 2)
        return Seat(row, column)
    }
}

data class Seat(val row: Int, val column: Int)


fun main() {
    println("Part 1:")
    Challenge5().part1()

    println("Part 2:")
    Challenge5().part2()
}