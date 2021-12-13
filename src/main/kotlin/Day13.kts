import java.io.File
import java.lang.Integer.max
import java.lang.Integer.min

val reader = File("src/main/resources/day13.in").bufferedReader()
val dots = mutableSetOf<Pair<Int, Int>>()
var maxX = 0
var maxY = 0

while (true) {
    val line = reader.readLine()
    if (line.isEmpty()) {
        break
    }

    val (x, y) = line.split(",").map { it.toInt() }
    maxX = max(x, maxX)
    maxY = max(y, maxY)
    dots.add(x to y)
}

var n = maxX + 1
var m = maxY + 1
val matrix = Array(n) { BooleanArray(m) }
dots.forEach { (x, y) -> matrix[x][y] = true }

fun foldUp(d: Int) {
    for (i in 0 until n) {
        for (j in 1.. min(d, m - d - 1)) {
            matrix[i][d - j] = matrix[i][d - j] || matrix[i][d + j]
        }
    }
    m = d
}

fun foldLeft(d: Int) {
    for (i in 1 .. min(d, n - d - 1)) {
        for (j in 0 until m) {
            matrix[d - i][j] = matrix[d - i][j] || matrix[d + i][j]

        }
    }
    n = d
}

fun print() {
    for (j in 0 until m) {
        for (i in 0 until n) {
            print(if (matrix[i][j]) '#' else '.')
            print(" ")
        }
        println()
    }
    println()
}

fun fold(line: String) {
    val (c, d) = line.split(" ", "=").takeLast(2)
    if (c == "y") {
        foldUp(d.toInt())
    } else {
        foldLeft(d.toInt())
    }
}

fold(reader.readLine())
var ans = 0
for (j in 0 until m) {
    for (i in 0 until n) {
        if (matrix[i][j]) ans++
    }
}
println(ans)

reader.forEachLine { fold(it) }
print()