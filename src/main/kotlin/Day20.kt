import java.io.File

fun get(m: MutableList<IntArray>, i: Int, j: Int, default: Int = 0): Int {
    if (i < 0 || j < 0 || i >= m.size || j >= m[0].size) {
        return default
    }

    return m[i][j]
}

fun getSquare(m: MutableList<IntArray>, i: Int, j: Int, default: Int = 0): Int {
    var v = 0
    for (k in 0 until 9) {
        v = v * 2 + get(m, i - 1 + k / 3, j - 1 + k % 3, default)
    }
    return v
}

fun litCount(m: MutableList<IntArray>): Int {
    var ans = 0
    for (i in m.indices) {
        ans += m[i].count { it == 1 }
    }
    return ans
}

fun main() {
    val reader = File("src/main/resources/day20.in").bufferedReader()
    val mapping = reader.readLine().map { if (it == '.') 0 else 1 }.toIntArray()
    reader.readLine()

    var m = mutableListOf<IntArray>()
    m.add(IntArray(0))
    reader.forEachLine { line -> m.add(".$line.".map { if (it == '.') 0 else 1 }.toIntArray()) }
    m[0] = IntArray(m[2].size) { 0 }
    m.add(IntArray(m[2].size) { 0 })
    for (step in 1..50) {
        val newM = mutableListOf<IntArray>()
        newM.add(IntArray(m[0].size + 2) { step % 2 })
        for (i in 0 until m.size) {
            newM.add(IntArray(m[i].size + 2) { step % 2 })
            for (j in 0 until m[0].size) {
                newM[i + 1][j + 1] = mapping[getSquare(m, i, j, default = (step + 1) % 2)]
            }
        }
        newM.add(IntArray(m[0].size + 2) { step % 2 })
        m = newM
        if (step == 2 || step == 50) {
            println(litCount(m))
        }
    }
}