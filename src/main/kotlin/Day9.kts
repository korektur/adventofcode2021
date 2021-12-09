import java.io.File
import java.util.Comparator
import java.util.PriorityQueue

val floor = ArrayList<IntArray>()
File("src/main/resources/day9.in").forEachLine {
    floor.add(it.toCharArray().map { c -> c.digitToInt() }.toIntArray())
}

var ans = 0
val visited = List(floor.size) { BooleanArray(floor[0].size) }
val sizes = PriorityQueue<Int>(Comparator.reverseOrder())

fun dfs(i: Int, j: Int): Int {
    if (visited[i][j] || floor[i][j] == 9) {
        return 0
    }

    visited[i][j] = true
    var curAns = 1
    if (i > 0 && floor[i][j] < floor[i - 1][j]) {
        curAns += dfs(i - 1, j)
    }

    if (i < floor.size - 1 && floor[i][j] < floor[i + 1][j]) {
        curAns += dfs(i + 1, j)
    }

    if (j > 0 && floor[i][j] < floor[i][j - 1]) {
        curAns += dfs(i, j - 1)
    }

    if (j < floor[i].size - 1 && floor[i][j] < floor[i][j + 1]) {
        curAns += dfs(i, j + 1)
    }

    return curAns
}

for (i in 0 until floor.size) {
    for (j in 0 until floor[0].size) {
        if ((i == 0 || floor[i][j] < floor[i - 1][j]) &&
            (i == floor.size - 1 || floor[i][j] < floor[i + 1][j]) &&
            (j == 0 || floor[i][j] < floor[i][j - 1]) &&
            (j == floor[i].size - 1 || floor[i][j] < floor[i][j + 1])
        ) {
            ans += floor[i][j] + 1
            sizes.add(dfs(i, j))
        }
    }
}

println(ans)
println(sizes.poll() * sizes.poll() * sizes.poll())