package nl.bramkohl.adventofcode

class Challenge11 {

    fun part1() {
        val newLayout = gameOfLife(initialLayout = parseInput(), ::shouldChangePart1)
        println("Occupied: ${newLayout.getAmountOfCharacter('#')}")
    }

    private fun shouldChangePart1(i: Int, j: Int, layout: List<List<Char>>): Boolean {
        if (layout[i][j] == '.') return false
        val amountOccupied: Int =
            amountOccupied(i - 1, j - 1, layout) +
                    amountOccupied(i - 1, j, layout) +
                    amountOccupied(i - 1, j + 1, layout) +
                    amountOccupied(i, j + 1, layout) +
                    amountOccupied(i + 1, j + 1, layout) +
                    amountOccupied(i + 1, j, layout) +
                    amountOccupied(i + 1, j - 1, layout) +
                    amountOccupied(i, j - 1, layout)
        return (layout[i][j] == 'L' && amountOccupied == 0) ||
                (layout[i][j] == '#' && amountOccupied >= 4)
    }


    private fun amountOccupied(i: Int, j: Int, layout: List<List<Char>>): Int =
        when {
            i !in layout.indices -> 0
            j !in layout[i].indices -> 0
            layout[i][j] == '#' -> 1
            else -> 0
        }

    fun part2() {
        val newLayout = gameOfLife(initialLayout = parseInput(), ::shouldChangePart2)
        println("Occupied: ${newLayout.getAmountOfCharacter('#')}")
    }

    private fun gameOfLife(
        initialLayout: List<List<Char>>,
        shouldChange: (Int, Int, List<List<Char>>) -> Boolean
    ): List<List<Char>> {
        var layout = initialLayout
        var change = true
        while (change) {
            change = false
            val newLayout: List<MutableList<Char>> = List(layout.size) { MutableList(layout[0].size) { '!' } }
            for (i in layout.indices) {
                for (j in layout[i].indices) {
                    if (shouldChange(i, j, layout)) {
                        newLayout[i][j] = if (layout[i][j] == '#') 'L' else '#'
                        change = true
                    } else {
                        newLayout[i][j] = layout[i][j]
                    }
                }
            }
            layout = newLayout
        }
        return layout
    }

    private fun shouldChangePart2(i: Int, j: Int, layout: List<List<Char>>): Boolean {
        if (layout[i][j] == '.') return false
        val amountOccupied: Int =
            amountOccupiedP2("TL", layout, i, j) +
                    amountOccupiedP2("T", layout, i, j) +
                    amountOccupiedP2("TR", layout, i, j) +
                    amountOccupiedP2("R", layout, i, j) +
                    amountOccupiedP2("BR", layout, i, j) +
                    amountOccupiedP2("B", layout, i, j) +
                    amountOccupiedP2("BL", layout, i, j) +
                    amountOccupiedP2("L", layout, i, j)
        return (layout[i][j] == 'L' && amountOccupied == 0) ||
                (layout[i][j] == '#' && amountOccupied >= 5)
    }

    private fun amountOccupiedP2(direction: String, layout: List<List<Char>>, i: Int, j: Int): Int {
        var currentI = updateI(direction, i)
        var currentJ = updateJ(direction, j)
        while (isValidPosition(currentI, currentJ, layout) && !isChair(currentI, currentJ, layout)) {
            currentI = updateI(direction, currentI)
            currentJ = updateJ(direction, currentJ)
        }
        return if (isValidPosition(currentI, currentJ, layout) && layout[currentI][currentJ] == '#') 1 else 0
    }

    private fun isChair(i: Int, j: Int, layout: List<List<Char>>) = layout[i][j] == 'L' || layout[i][j] == '#'

    private fun isValidPosition(i: Int, j: Int, layout: List<List<Char>>): Boolean =
        i in layout.indices && j in layout[i].indices

    private fun updateI(direction: String, i: Int) = when (direction) {
        "T", "TL", "TR" -> i - 1
        "B", "BL", "BR" -> i + 1
        else -> i
    }

    private fun updateJ(direction: String, j: Int) = when (direction) {
        "L", "TL", "BL" -> j - 1
        "R", "TR", "BR" -> j + 1
        else -> j
    }

    private fun parseInput(): List<List<Char>> {
        val fileContent = javaClass.classLoader.getResource("input11.txt")!!.readText()
        return fileContent.split(System.lineSeparator()).map { string ->
            string.toCharArray().toList()
        }
    }
}

fun List<List<Char>>.getAmountOfCharacter(char: Char) = this.map { row ->
    row.filter { c -> c == char }.count()
}.sum()

fun List<List<Char>>.print() {
    println("-----------------------------------")
    this.forEach { row ->
        row.forEach { character ->
            print(character)
        }
        println()
    }
    println("-----------------------------------")
}

fun main() {
    println("Part 1:")
    Challenge11().part1()

    println("Part 2:")
    Challenge11().part2()
}