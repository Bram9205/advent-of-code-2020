package nl.bramkohl.adventofcode

import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

class Challenge12 {

    fun part1() {
        val instructions = parseInput()
        var northSouth = 0
        var eastWest = 0
        var facing = 90
        instructions.forEach { navInstruction ->
            when (navInstruction.action) {
                'N' -> northSouth += navInstruction.value
                'E' -> eastWest += navInstruction.value
                'S' -> northSouth -= navInstruction.value
                'W' -> eastWest -= navInstruction.value
                'L' -> facing = (((facing - navInstruction.value) % 360) + 360) % 360
                'R' -> facing = (facing + navInstruction.value) % 360
                'F' -> {
                    when (facing) {
                        0 -> northSouth += navInstruction.value
                        90 -> eastWest += navInstruction.value
                        180 -> northSouth -= navInstruction.value
                        270 -> eastWest -= navInstruction.value
                    }
                }
            }
        }
        println("Manhattan distance of NorthSouth: $northSouth EastWest: $eastWest = ${abs(northSouth) + abs(eastWest)}")
    }

    fun part2() {
        val instructions = parseInput()
        var northSouth = 0
        var eastWest = 0
        var waypointNorthSouth = 1
        var waypointEastWest = 10
        instructions.forEach { navInstruction ->
            when (navInstruction.action) {
                'N' -> waypointNorthSouth += navInstruction.value
                'E' -> waypointEastWest += navInstruction.value
                'S' -> waypointNorthSouth -= navInstruction.value
                'W' -> waypointEastWest -= navInstruction.value
                'L' -> {
                    val (newX, newY) = rotate2dOrigin(waypointEastWest, waypointNorthSouth, navInstruction.value)
                    waypointEastWest = newX
                    waypointNorthSouth = newY
                }
                'R' -> {
                    val (newX, newY) = rotate2dOrigin(waypointEastWest, waypointNorthSouth, -navInstruction.value)
                    waypointEastWest = newX
                    waypointNorthSouth = newY
                }
                'F' -> {
                    northSouth += waypointNorthSouth * navInstruction.value
                    eastWest += waypointEastWest * navInstruction.value
                }
            }
        }
        println("Manhattan distance of NorthSouth: $northSouth EastWest: $eastWest = ${abs(northSouth) + abs(eastWest)}")
    }

    private fun rotate2dOrigin(x: Int, y: Int, value: Int): Pair<Int, Int> {
        return Pair(
            (cos((value / 180.0) * Math.PI) * x - sin((value / 180.0) * Math.PI) * y).roundToInt(),
            (sin((value / 180.0) * Math.PI) * x + cos((value / 180.0) * Math.PI) * y).roundToInt()
        )
    }

    private fun parseInput(): List<NavInstruction> {
        val fileContent = javaClass.classLoader.getResource("input12.txt")!!.readText()
        return fileContent.split(System.lineSeparator()).map { string ->
            NavInstruction(string[0], Integer.parseInt(string.substring(1)))
        }
    }
}

data class NavInstruction(val action: Char, val value: Int)

fun main() {
    println("Part 1:")
    Challenge12().part1()

    println("Part 2:")
    Challenge12().part2()
}