import java.io.File

fun main() {
    val sea = listOf<MutableList<CharArray>>(mutableListOf(), mutableListOf())
    File("src/main/resources/day25.in").forEachLine { line ->
        sea[0].add(line.toCharArray())
        sea[1].add(CharArray(sea[0][0].size) { '.' })
    }

    var ans = 0
    var moved = true
    while (moved) {
        moved = false
        val from = sea[ans % 2]
        val to = sea[(ans + 1) % 2]

        to.forEach { it.fill('.') }

        for (i in from.indices) {
            for (j in from[i].indices) {
                if (from[i][j] != '>') continue

                if (from[i][(j + 1) % from[i].size] == '.') {
                    to[i][(j + 1) % from[i].size] = '>'
                    moved = true
                } else {
                    to[i][j] = '>'
                }
            }
        }

        for (i in from.indices) {
            for (j in from[i].indices) {
                if (from[i][j] != 'v') continue

                if (from[(i + 1) % from.size][j] != 'v' && to[(i + 1) % from.size][j] == '.') {
                    to[(i + 1) % from.size][j] = 'v'
                    moved = true
                } else {
                    to[i][j] = 'v'
                }
            }
        }
        ans++
    }

    println(ans)
}