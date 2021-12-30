import java.io.File
import java.io.InputStream
import kotlin.math.floor

fun main(args: Array<String>) {
    val inputStream: InputStream = File("../input/day01.txt").inputStream()

    var sumPart1 = 0.0
    var sumPart2 = 0.0
    inputStream.bufferedReader().useLines { lines ->
        lines.forEach {
            val fuel4module = floor(it.toInt()/3.0) - 2

            sumPart1 += fuel4module
            sumPart2 += totalFuel(fuel4module)
        }
    }
    println("Part 1 Result> ${sumPart1.toInt()}")
    println("Part 2 Result> ${sumPart2.toInt()}")
}

fun totalFuel(fuel: Double):Double {
    val fuel4fuel = floor(fuel/3.0) - 2
    return if (fuel4fuel>0) {
        fuel + totalFuel(fuel4fuel)
    } else {
        fuel
    }
}
