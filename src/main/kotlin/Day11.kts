import java.io.File

val m = Array(10) { IntArray(10) }
val lines = File("src/main/resources/day11.in").readLines()
lines.forEachIndexed { i, s -> s.forEachIndexed { j, c -> m[i][j] = c.digitToInt() } }
var ans = 0
var diff = 0

fun simulate(i: Int, j: Int) {
    if (i < 0 || j < 0 || i >= m.size || j >= m[0].size) {
        return
    }

    if (++m[i][j] == 10) {
        ans++
        diff++
        simulate(i - 1, j)
        simulate(i + 1, j)
        simulate(i, j + 1)
        simulate(i, j - 1)
        simulate(i - 1, j - 1)
        simulate(i - 1, j + 1)
        simulate(i + 1, j - 1)
        simulate(i + 1, j + 1)
    }
}

for (step in 1..1000) {
    for (i in m.indices) {
        for (j in m[0].indices) {
            simulate(i, j)
        }
    }
    for (i in m.indices) {
        for (j in m[0].indices) {
            if (m[i][j] >= 10) {
                m[i][j] = 0
            }
        }
    }

    if (step == 100) {
        println(ans)
    }

    if (diff == m.size * m[0].size) {
        println(step)
        break
    }
    diff = 0
}

