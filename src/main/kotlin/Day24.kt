import java.io.File

fun run(part2: Boolean): Any {
    val input = File("src/main/resources/day24.in").readLines().map { it.split(" ") }
    val blocks = input.chunked(input.size / 14)

    val ans = MutableList(14) { -1 }
    val buffer = ArrayDeque<Pair<Int, Int>>()
    val d = if (part2) 1 else 9
    for (i in blocks.indices) {
        val instructions = blocks[i]
        if (instructions.any { it[0] == "div" && it[1] == "z" && it[2] == "1" }) {
            val offset = i to instructions.last { it[0] == "add" && it[1] == "y" }[2].toInt()
            buffer.addFirst(offset)
            continue
        }

        val offset = instructions.last { it[0] == "add" && it[1] == "x" }[2].toInt()
        val (j, lastOffset) = buffer.removeFirst()
        val diff = offset + lastOffset

        ans[j] = d + if ((diff >= 0 && !part2) || (diff < 0 && part2)) -diff else 0
        ans[i] = d + if ((diff >= 0 && part2) || (diff < 0 && !part2)) diff else 0
    }


    return ans.joinToString("").toLong()
}

fun main() {
    println(run(false))
    println(run(true))
}