package nl.bramkohl.adventofcode

class Challenge2 {

    fun printAmountOfCorrectPasswords() {
        println("Count: ${parseInput().filter { line -> checkPasswordValidity(line.password, line.policy) }.count()}")
    }

    fun printAmountOfCorrectPasswordsPart2() {
        println("Count: ${parseInput().filter { line -> checkPasswordValidity2(line.password, line.policy) }.count()}")
    }

    private fun checkPasswordValidity(password: String, policy: PasswordPolicy) =
        password.toCharArray().filter { c -> c == policy.character }.count().let { count ->
            count >= policy.min && count <= policy.max
        }

    private fun checkPasswordValidity2(password: String, policy: PasswordPolicy) =
        (password[policy.min - 1] == policy.character).xor(password[policy.max - 1] == policy.character)

    private fun parseInput(): List<InputLine> {
        val fileContent = javaClass.classLoader.getResource("input2.txt")!!.readText()
        return fileContent.split(System.lineSeparator()).map { lineString ->
            val policyString = lineString.split(":")[0]
            val policy = PasswordPolicy(
                min = Integer.parseInt(policyString.split("-")[0]),
                max = Integer.parseInt(policyString.split(" ")[0].split("-")[1]),
                character = policyString.split(" ")[1][0]
            )
            InputLine(lineString.split(": ")[1], policy)
        }
    }
}

data class PasswordPolicy(val min: Int, val max: Int, val character: Char)

data class InputLine(val password: String, val policy: PasswordPolicy)

fun main(args: Array<String>) {
    Challenge2().printAmountOfCorrectPasswordsPart2()
}