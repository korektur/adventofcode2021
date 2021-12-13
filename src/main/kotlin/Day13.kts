import java.io.File
import kotlin.math.abs

val reader = File("src/main/resources/day13.in").bufferedReader()
var dots = mutableSetOf<Pair<Int, Int>>()

while (true) {
    val line = reader.readLine()
    if (line.isEmpty()) {
        break
    }

    val (x, y) = line.split(",").map { it.toInt() }
    dots.add(x to y)
}

fun foldUp(p: Pair<Int, Int>, d: Int): Pair<Int, Int> {
    return p.first to (d - abs(p.second - d))
}

fun foldLeft(p: Pair<Int, Int>, d: Int): Pair<Int, Int> {
    return (d - abs(p.first - d)) to p.second
}

fun print() {
    val n = dots.maxOf { it.first }
    val m = dots.maxOf { it.second }
    for (j in 0..m) {
        for (i in 0..n) {
            print(if (dots.contains(i to j)) "# " else ". ")
        }
        println()
    }
    println()
}

fun fold(line: String) {
    val (c, d) = line.split(" ", "=").takeLast(2)
    dots = dots.map {
        when (c) {
            "y" -> foldUp(it, d.toInt())
            else -> foldLeft(it, d.toInt())
        }
    }.toMutableSet()
}

fold(reader.readLine())
println(dots.size)

reader.forEachLine { fold(it) }
print()