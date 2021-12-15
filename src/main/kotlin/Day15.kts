import java.io.File

val m = ArrayList<MutableList<Int>>()
File("src/main/resources/day15.in").forEachLine { line ->
    val arr = line.toCharArray().map { it.digitToInt() }.toMutableList()
    val l = (0..4)
        .map { IntArray(arr.size) { i -> it + arr[i] }.map { if (it > 9) it - 9 else it }.toMutableList() }
        .reduce { acc, ints -> acc.addAll(ints); acc }
    m.add(l)
}

val n = m.size
for (i in 1..4) {
    for (j in 0 until n) {
        m.add(m[j].map { it + i }.map { if (it > 9) it - 9 else it }.toMutableList())
    }
}

val d = Array(m.size) { IntArray(m[0].size) { Int.MAX_VALUE } }
val q = ArrayDeque<Pair<Int, Int>>()
d[0][0] = 0
q.addLast(0 to 0)

while (q.isNotEmpty()) {
    val (i, j) = q.removeFirst()
    if (i < m.size - 1 && d[i][j] + m[i + 1][j] < d[i + 1][j]) {
        d[i + 1][j] = d[i][j] + m[i + 1][j]
        q.addLast(i + 1 to j)
    }

    if (j < m[0].size - 1 && d[i][j] + m[i][j + 1] < d[i][j + 1]) {
        d[i][j + 1] = d[i][j] + m[i][j + 1]
        q.addLast(i to j + 1)
    }

    if (i > 0 && d[i][j] + m[i - 1][j] < d[i - 1][j]) {
        d[i - 1][j] = d[i][j] + m[i - 1][j]
        q.addLast(i - 1 to j)
    }

    if (j > 0 && d[i][j] + m[i][j - 1] < d[i][j - 1]) {
        d[i][j - 1] = d[i][j] + m[i][j - 1]
        q.addLast(i to j - 1)
    }
}

println(d[n - 1][n - 1])
println(d.last().last())