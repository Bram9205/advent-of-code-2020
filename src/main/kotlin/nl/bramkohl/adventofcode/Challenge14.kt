package nl.bramkohl.adventofcode

class Challenge14 {

    fun part1() {
        val program = parseInput()
        var mask = ""
        val memory: HashMap<Long, Long> = HashMap()
        program.forEach { instruction ->
            val split = instruction.split(" = ")
            if (split[0] == "mask") {
                mask = split[1]
            } else {
                memory[split[0].substringAfter('[').substringBefore(']').toLong()] =
                    applyMaskIgnoreX(mask, split[1].toLong())
            }
        }
        println("Sum: ${memory.values.sum()}")
    }

    private fun applyMaskIgnoreX(mask: String, value: Long): Long {
        val orMask = mask.replace('X', '0').toLong(2)
        val andMask = mask.replace('X', '1').toLong(2)
        return (value or orMask) and andMask
    }

    fun part2() {
        val program = parseInput()
        var mask = ""
        val memory: HashMap<Long, Long> = HashMap()
        program.forEach { instruction ->
            val split = instruction.split(" = ")
            if (split[0] == "mask") {
                mask = split[1]
            } else {
                getMemoryAddresses(
                    mask,
                    split[0].substringAfter('[').substringBefore(']').toLong()
                ).forEach { address ->
                    memory[address] = split[1].toLong()
                }
            }
        }

        println("Sum: ${memory.values.sum()}")
    }

    private fun getMemoryAddresses(mask: String, value: Long): List<Long> {
        // Use X for values we can ignore so we can use function from part 1 (use F for floating bits):
        val maskWithFloats = mask.replace('X', 'F').replace('0', 'X')

        val masks: MutableList<String> = mutableListOf()
        replaceFloatsAndAddToResult(maskWithFloats, masks)

        return masks.map { curMask -> applyMaskIgnoreX(curMask, value) }
    }

    private fun replaceFloatsAndAddToResult(maskWithFloats: String, result: MutableList<String>) {
        if (!maskWithFloats.contains('F')) {
            result.add(maskWithFloats)
        } else {
            replaceFloatsAndAddToResult(maskWithFloats.replaceFirst('F', '0'), result)
            replaceFloatsAndAddToResult(maskWithFloats.replaceFirst('F', '1'), result)
        }
    }

    private fun parseInput() =
        javaClass.classLoader.getResource("input14.txt")!!.readText().split(System.lineSeparator())
}

fun main() {
    println("Part 1:")
    Challenge14().part1()

    println("Part 2:")
    Challenge14().part2()
}