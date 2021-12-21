import java.io.File
import kotlin.math.min

var s = 0

fun throwDeterministic(x: Int): Int {
    val newValue = (x + 3 * (s % 100 + 1) + 3) % 10
    s += 3
    return newValue
}

fun main() {
    var (x, y) = File("src/main/resources/day21.in").readLines()
        .map { it.split(": ") }
        .map { it[1].toInt() }
    var score1 = 0
    var score2 = 0
    x--
    y--
    while (true) {
        x = throwDeterministic(x)
        score1 += x + 1
        if (score1 >= 1000) break

        y = throwDeterministic(y)
        score2 += y + 1
        if (score2 >= 1000) break
    }

    println(min(score1, score2) * s)}