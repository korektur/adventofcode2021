import java.io.File

val reader = File("src/main/resources/day14.in").bufferedReader()
var sequence: String = reader.readLine()

val m = reader.lineSequence().drop(1)
    .map { it.split(" -> ") }
    .associate { it[0] to it[1] }
    .toMutableMap()

var pairs = mutableMapOf<String, Long>()
for (i in 0 until sequence.length - 1) {
    pairs.merge("" + sequence[i] + sequence[i + 1], 1L, Long::plus)
}
val counts = sequence.groupBy { it }.mapValues { it.value.count().toLong() }.toMutableMap()

for (i in 1..40) {
    val newPairs = mutableMapOf<String, Long>()
    for (e in pairs) {
        val k = m[e.key]!!
        newPairs.merge(e.key[0] + k, e.value, Long::plus)
        newPairs.merge(k + e.key[1], e.value, Long::plus)
        counts.merge(k[0], e.value, Long::plus)
    }
    pairs = newPairs

    if (i == 10 || i == 40) {
        println(counts.maxOf { it.value } - counts.minOf { it.value })
    }
}