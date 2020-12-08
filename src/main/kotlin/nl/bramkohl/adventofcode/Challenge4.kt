package nl.bramkohl.adventofcode

import java.util.regex.Pattern

class Challenge4 {


    fun part1() {
        val passports = parseInput()

        val valid = passports.filter(validPassportPart1()).count()
        println("multiplied: $valid")
    }

    fun part2() {
        val passports = parseInput()

        val valid = passports.filter(validPassportPart1()).filter(validPassportPart2()).count()
        println("multiplied: $valid")
    }

    private fun validPassportPart1(): (Passport) -> Boolean {
        return { passport ->
            passport.byr.isNotEmpty()
                    && passport.iyr.isNotEmpty()
                    && passport.eyr.isNotEmpty()
                    && passport.hgt.isNotEmpty()
                    && passport.hcl.isNotEmpty()
                    && passport.ecl.isNotEmpty()
                    && passport.pid.isNotEmpty()
        }
    }

    private fun validPassportPart2(): (Passport) -> Boolean {
        return { passport ->
            validPassportPart1()(passport)
                    && Integer.parseInt(passport.byr) in 1920..2002
                    && Integer.parseInt(passport.iyr) in 2010..2020
                    && Integer.parseInt(passport.eyr) in 2020..2030
                    && validheight(passport.hgt)
                    && Pattern.matches("#[0-9a-f]{6}", passport.hcl)
                    && arrayOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(passport.ecl)
                    && Pattern.matches("[0-9]{9}", passport.pid)
        }
    }

    private fun validheight(hgt: String): Boolean {
        return when {
            hgt.contains("in") -> Integer.parseInt(hgt.split("in")[0]) in 59..76
            hgt.contains("cm") -> Integer.parseInt(hgt.split("cm")[0]) in 150..193
            else -> false
        }
    }

    private fun parseInput(): List<Passport> {
        val fileContent = javaClass.classLoader.getResource("input4.txt")!!.readText()
        return fileContent.split(System.lineSeparator() + System.lineSeparator()).map { passportString ->
            toPassport(passportString)
        }
    }

    private fun toPassport(passportString: String): Passport {
        val result = Passport()
        passportString.split(System.lineSeparator()).forEach { part ->
            part.split(" ").forEach { field ->
                val splitField = field.split(":")
                when (splitField[0]) {
                    "byr" -> result.byr = splitField[1]
                    "iyr" -> result.iyr = splitField[1]
                    "eyr" -> result.eyr = splitField[1]
                    "hgt" -> result.hgt = splitField[1]
                    "hcl" -> result.hcl = splitField[1]
                    "ecl" -> result.ecl = splitField[1]
                    "pid" -> result.pid = splitField[1]
                    "cid" -> result.cid = splitField[1]
                }
            }
        }
        return result
    }
}

data class Passport(
    var byr: String = "",
    var iyr: String = "",
    var eyr: String = "",
    var hgt: String = "",
    var hcl: String = "",
    var ecl: String = "",
    var pid: String = "",
    var cid: String = ""
)


fun main(args: Array<String>) {
    println("Part 1:")
    Challenge4().part1()

    println("Part 2:")
    Challenge4().part2()
}