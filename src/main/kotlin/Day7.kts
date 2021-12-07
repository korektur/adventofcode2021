import java.io.File
import java.lang.Integer.min
import kotlin.math.abs
import kotlin.math.roundToInt

val positions = File("src/main/resources/day7.in").readLines()[1]
    .split(",")
    .map { it.toInt() }
    .sorted()

val median = positions[positions.size / 2]
var ans = 0
positions.forEach { ans += abs(median - it) }
println(ans)

var avg = positions.average().toInt()
var ans1 = positions.map { abs(it - avg) }.sumOf { it * (it + 1) / 2 }
var ans2 = positions.map { abs(it - avg - 1) }.sumOf { it * (it + 1) / 2 }
println(min(ans1, ans2))