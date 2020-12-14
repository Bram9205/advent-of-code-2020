package nl.bramkohl.adventofcode

class Challenge3 {

    fun printAmountOfTreesOnSlope(slope: Slope) {
        val lines = parseInput()
        val trees = getNumberOfTreesOnSlope(lines, slope)
        println("Count: $trees")
    }

    fun part2() {
        val lines = parseInput()
        val multiplied =
            getNumberOfTreesOnSlope(lines, Slope(1,1)) *
            getNumberOfTreesOnSlope(lines, Slope(3,1)) *
            getNumberOfTreesOnSlope(lines, Slope(5,1)) *
            getNumberOfTreesOnSlope(lines, Slope(7,1)) *
            getNumberOfTreesOnSlope(lines, Slope(1,2))
        println("multiplied: $multiplied")
    }

    private fun getNumberOfTreesOnSlope(lines: List<String>, slope: Slope): Int {
        var x = 0
        var y = 0
        var trees = 0
        while (y < lines.size) {
            if (lines[y][x] == '#') trees++
            x = (x + slope.right) % 31
            y += slope.down
        }
        return trees
    }

    private fun parseInput(): List<String> {
        val fileContent = javaClass.classLoader.getResource("input3.txt")!!.readText()
        return fileContent.split(System.lineSeparator())
    }
}

data class Slope(val right: Int, val down: Int)

fun main() {
    Challenge3().printAmountOfTreesOnSlope(Slope(3, 1))
    Challenge3().part2()
}