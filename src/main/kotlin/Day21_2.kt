import java.io.File

val outcomes = mapOf(3 to 1, 4 to 3, 5 to 6, 6 to 7, 7 to 6, 8 to 3, 9 to 1)

data class CacheKey(val position1: Int, val position2: Int, val score1: Int, val score2: Int, val player: Int)

val cache = mutableMapOf<CacheKey, LongArray>()
fun count(positions: IntArray, score: IntArray, player: Int): LongArray {
    if (score[0] >= 21) return longArrayOf(1, 0)
    if (score[1] >= 21) return longArrayOf(0, 1)

    val cacheKey = CacheKey(positions[0], positions[1], score[0], score[1], player)
    if (cache.containsKey(cacheKey)) {
        return cache[cacheKey]!!
    }

    val res = LongArray(2)
    for (outcome in outcomes) {
        val newPositions = positions.copyOf()
        val newScores = score.copyOf()
        newPositions[player] = (positions[player] + outcome.key) % 10
        newScores[player] += newPositions[player] + 1
        val furtherRes = count(newPositions, newScores, (player + 1) % 2)
        res[0] += furtherRes[0] * outcome.value
        res[1] += furtherRes[1] * outcome.value
    }

    cache[cacheKey] = res
    return res
}

fun main() {
    var positions = File("src/main/resources/day21.in").readLines()
        .map { it.split(": ")[1].toInt() - 1 }
        .toIntArray()

    val res = count(positions, intArrayOf(0, 0), player = 0)
    println(res.maxOrNull())
}