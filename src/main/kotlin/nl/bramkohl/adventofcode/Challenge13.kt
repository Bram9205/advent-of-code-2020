package nl.bramkohl.adventofcode

class Challenge13 {

    fun part1() {
        val (arrivalTime, busses) = parseInput()
        val waitTimesPerId = busses.map { bus -> Pair(bus.id, bus.id - (arrivalTime % bus.id)) }
        val (id, waitTime) = waitTimesPerId.minByOrNull { it.second }!!
        println("First bus to depart has id '$id', wait time: $waitTime seconds. Product: ${id * waitTime}")
    }

    fun part2() {
        val busses = parseInput().second

        var timeStamp = busses.first().id
        for (i in 1 until busses.size) {
            for (j in timeStamp..Long.MAX_VALUE step busses.take(i).map(Bus::id).reduce { acc, busId -> acc * busId }) {
                if ((j + busses[i].index) % busses[i].id == 0L) {
                    timeStamp = j
                    break
                }
            }
        }
        println(timeStamp)
    }

    private fun parseInput(): Pair<Int, MutableList<Bus>> {
        val fileContent = javaClass.classLoader.getResource("input13.txt")!!.readText().split(System.lineSeparator())
        val busLines = mutableListOf<Bus>()
        fileContent[1].split(",").forEachIndexed { index, it ->
            if (it != "x") busLines.add(Bus(index, it.toLong()))
        }
        return Pair(fileContent[0].toInt(), busLines)
    }
}

data class Bus(val index: Int, val id: Long)

fun main() {
    println("Part 1:")
    Challenge13().part1()

    println("Part 2:")
    Challenge13().part2()
}