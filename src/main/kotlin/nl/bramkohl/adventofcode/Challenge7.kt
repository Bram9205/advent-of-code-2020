package nl.bramkohl.adventofcode

class Challenge7 {

    fun part1() {
        val bagMap = parseInput()
        var lastCount = 0
        val result = HashSet<String>()
        result.add("shiny gold")
        while (lastCount != result.size) {
            lastCount = result.size
            bagMap.forEach { (bag, bagList) ->
                if (bagList.map { bagAmount -> bagAmount.name }.intersect(result).isNotEmpty()) {
                    result.add(bag)
                }
            }
        }
        println("Total: ${result.count() - 1}")
    }

    fun part2() {
        val bagMap: Map<String, List<BagAmount>> = parseInput()
        println("Amount in shiny gold: ${getAmountOfBagsIn(bagMap, "shiny gold")}")
    }

    private fun getAmountOfBagsIn(bagMap: Map<String, List<BagAmount>>, name: String): Int {
        var result = 0
        (bagMap[name] ?: error("No entry for '$name' in bagMap")).forEach { bagAmount ->
            result += bagAmount.amount
            result += bagAmount.amount * getAmountOfBagsIn(bagMap, bagAmount.name)
        }
        return result
    }


    private fun parseInput(): Map<String, List<BagAmount>> {
        val fileContent = javaClass.classLoader.getResource("input7.txt")!!.readText()
        val map = HashMap<String, List<BagAmount>>()
        fileContent.split(System.lineSeparator()).forEach { line ->
            val split = line.split(" bags contain ")
            map[split[0]] = toBagList(split[1])
        }
        return map
    }

    private fun toBagList(stringList: String): List<BagAmount> {
        if (stringList == "no other bags.") {
            return emptyList()
        }
       return stringList.split(", ").map { bagAmount ->
            BagAmount(
                Integer.parseInt(bagAmount.split(" ")[0]),
                bagAmount.split(" ").take(3).takeLast(2).joinToString(" ")
            )
        }
    }
}

data class BagAmount(val amount: Int, val name: String)

fun main(args: Array<String>) {
    println("Part 1:")
    Challenge7().part1()

    println("Part 2:")
    Challenge7().part2()
}