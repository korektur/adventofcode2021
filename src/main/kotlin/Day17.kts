import java.io.File
import kotlin.math.max

val input = File("src/main/resources/day17.in").readLines()[0]
    .removePrefix("target area: ")
    .split(", y=", "..", "x=")

val minX = input[1].toInt()
val maxX = input[2].toInt()
val minY = input[3].toInt()
val maxY = input[4].toInt()

var ans = Int.MIN_VALUE
var ans2 = 0
for (xV in 1..maxX) {
    for (yV in minY..5000) {
        var curX = 0
        var curY = 0
        var step = 0
        var curAns = 0
        while (curX <= maxX && curY >= minY) {
            curX += max(xV - step, 0)
            curY += yV - step
            step++
            curAns = max(curAns, curY)
            if (curX in minX..maxX && curY in minY..maxY) {
                ans = max(curAns, ans)
                ans2++
                break
            }
        }
    }
}

println(ans)
println(ans2)
