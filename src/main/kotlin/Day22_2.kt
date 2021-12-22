import java.io.File
import kotlin.math.min


fun main() {
    val steps = File("src/main/resources/day22.in").readLines()
        .map { readStep(it) }
        .toList()
    var xAxis = mutableListOf<Int>()
    var yAxis = mutableListOf<Int>()
    var zAxis = mutableListOf<Int>()
    var minX = Int.MAX_VALUE
    var minY = Int.MAX_VALUE
    var minZ = Int.MAX_VALUE
    for (step in steps) {
        minX = min(minX, step.x[0])
        minY = min(minY, step.y[0])
        minZ = min(minZ, step.z[0])
    }

    for (step in steps) {
        step.x[0] += minX
        step.x[1] += minX
        step.y[0] += minY
        step.y[1] += minY
        step.z[0] += minZ
        step.z[1] += minZ
        xAxis.add(step.x[0])
        xAxis.add(step.x[1])
        yAxis.add(step.y[0])
        yAxis.add(step.y[1])
        zAxis.add(step.z[0])
        zAxis.add(step.z[1])
    }

    xAxis = xAxis.asSequence().map { listOf(it, it + 1) }.flatten().distinct().sorted().toMutableList();
    yAxis = yAxis.asSequence().map { listOf(it, it + 1) }.flatten().distinct().sorted().toMutableList()
    zAxis = zAxis.asSequence().map { listOf(it, it + 1) }.flatten().distinct().sorted().toMutableList()
    val xMap = xAxis.mapIndexed { index, it -> index to it }
        .associateBy(keySelector = { it.second }, valueTransform = { it.first })
    val yMap = yAxis.mapIndexed { index, it -> index to it }
        .associateBy(keySelector = { it.second }, valueTransform = { it.first })
    val zMap = zAxis.mapIndexed { index, it -> index to it }
        .associateBy(keySelector = { it.second }, valueTransform = { it.first })

    val d = Array(xAxis.size) { Array(yAxis.size) { BooleanArray(zAxis.size) } }

    for (step in steps) {
        for (x in xMap[step.x[0]]!! until xMap[step.x[1] + 1]!!) {
            for (y in yMap[step.y[0]]!! until yMap[step.y[1] + 1]!!) {
                for (z in zMap[step.z[0]]!! until zMap[step.z[1] + 1]!!) {
                    d[x][y][z] = step.on
                }
            }
        }
    }

    var ans = 0L
    for (x in d.indices) {
        for (y in d[x].indices) {
            for (z in d[x][y].indices) {
                if (d[x][y][z]) {
                    ans += (xAxis[x + 1] - xAxis[x]).toLong() * (yAxis[y + 1] - yAxis[y]).toLong() * (zAxis[z + 1] - zAxis[z]).toLong()
                }
            }
        }
    }
    println(ans)
}