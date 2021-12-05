import java.io.File
import java.lang.Integer.min
import kotlin.math.abs
import kotlin.math.max

var max = 0
val list = ArrayList<IntArray>()
File("src/main/resources/day5.in").forEachLine {
    list.add(it.split(",", " -> ").map { it.toInt() }.toIntArray())
    max = max(max, list.last().maxOrNull()!!)
}

var field = Array(max + 1, init = { IntArray(max + 1) })
list.forEach {
    if (it[0] == it[2] || it[1] == it[3]) {
        for (i in min(it[0], it[2])..max(it[0], it[2])) {
            for (j in min(it[1], it[3])..max(it[1], it[3])) {
                field[j][i]++
            }
        }
    } else {
        val diff = abs(it[0] - it[2])
        for (d in 0..diff) {
            val j = it[1] + (if (it[1] < it[3]) d else -d)
            val i = it[0] + (if (it[0] < it[2]) d else -d)
            field[j][i]++
        }
    }
}
var ans = 0
field.forEach {
    it.forEach {
        if (it > 1) {
            ans++
        }
    }
}

println(ans)