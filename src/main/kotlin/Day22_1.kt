import java.io.File

@Suppress("ArrayInDataClass")
data class Step(val on: Boolean, val x: IntArray, val y: IntArray, val z: IntArray)

fun readStep(line: String): Step {
    val arr = line.split(" ", ",")
    val isOn = arr[0] == "on"
    val x = arr[1].drop(2).split("..").map { it.toInt() }.toIntArray()
    val y = arr[2].drop(2).split("..").map { it.toInt() }.toIntArray()
    val z = arr[3].drop(2).split("..").map { it.toInt() }.toIntArray()
    return Step(isOn, x, y, z)
}

fun main() {
    val steps = File("src/main/resources/day22.in").readLines()
        .map { readStep(it) }
        .toList()
    val d = Array(101) { Array(101) { BooleanArray(101) { false } } }

    for (step in steps) {
        for(x in step.x[0]..step.x[1]) {
            for(y in step.y[0]..step.y[1]) {
                for(z in step.z[0]..step.z[1]) {
                    d[x + 50][y + 50][z + 50] = step.on
                }
            }
        }
    }

    var ans = 0
    for(x in 0..100) {
        for(y in 0..100) {
            for(z in 0..100) {
                ans += if(d[x][y][z]) 1 else 0
            }
        }
    }
    println(ans)
}