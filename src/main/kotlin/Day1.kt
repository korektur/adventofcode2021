import java.io.File

fun main() {
    val m = ArrayList<Int>()
    File("src/main/resources/day1.in").forEachLine { m.add(it.toInt()) }
    val ans = m.filterIndexed{ index, e -> index != 0 && e > m[index - 1] }.count()
    println(ans)

    val ans2 = m.filterIndexed{ index, e -> index > 2 && e > m[index - 3] }.count()
    println(ans2)
}