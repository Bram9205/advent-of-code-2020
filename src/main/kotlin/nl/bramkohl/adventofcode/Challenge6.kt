package nl.bramkohl.adventofcode

class Challenge6 {

    fun part1() {
        val total = parseInput().map { group ->
            countUniqueCharacters(group.joinToString(""))
        }.reduce { acc, i -> acc + i }
        println("Total: $total")
    }

    private fun countUniqueCharacters(group: String): Int =
        group.toCharArray().distinct().count()

    fun part2() {
        val total = parseInput().map { group ->
            countCommonCharacters(group)
        }.reduce { acc, i -> acc + i }
        println("Total: $total")
    }

    private fun countCommonCharacters(group: List<String>): Int =
        group.map { person -> person.toCharArray().toHashSet() }
            .reduce { acc: Set<Char>, groupSet: HashSet<Char> -> acc.intersect(groupSet) }.count()

    private fun parseInput(): List<List<String>> {
        val fileContent = javaClass.classLoader.getResource("input6.txt")!!.readText()
        return fileContent.split(System.lineSeparator() + System.lineSeparator()).map { group ->
            group.split(System.lineSeparator())
        }
    }
}

fun main() {
    println("Part 1:")
    Challenge6().part1()

    println("Part 2:")
    Challenge6().part2()
}