import java.io.File

fun calculate(nums: ArrayList<String>, fst: Char, snd: Char) : Int {
    var x: List<String> = ArrayList<String>(nums)
    var i = 0
    while(x.size > 1) {
        var cnt = 0
        x.forEach { if (it[i] == '1') cnt++ }
        val c = if (cnt * 2 >= x.size) fst else snd
        x = x.filter { it[i] == c }.toList()

        i = (i + 1) % x[0].length
    }

    return x.first().toInt(2)
}

fun main() {
    val d = ArrayList<String>()
    File("src/main/resources/day3.in").forEachLine { d.add(it) }
    val counts = IntArray(d[0].length)
    var ans1 = 0
    var ans2 = 0
    d.forEach { it.forEachIndexed { index, c -> if (c == '1') counts[index]++ } }
    for (i in counts) {
        ans1 *= 2
        ans2 *= 2
        if ( i > d.size / 2 ) {
            ans1 += 1
        } else {
            ans2 += 1
        }
    }

    println(ans1 * ans2)

    println(calculate(d, '1', '0') * calculate(d, '0', '1'))

}