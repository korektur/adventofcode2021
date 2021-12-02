import java.io.File

fun main() {
    val d = ArrayList<String>()
    File("src/main/resources/day2.in").forEachLine { d.add(it) }
    var x = 0
    var y = 0
    d.map { it.split(" ") }.forEach {
        when (it[0]) {
            "up" -> y -= it[1].toInt()
            "down" -> y += it[1].toInt()
            "forward" -> x += it[1].toInt()
        }
    }

    println(x * y)

    var x2 = 0L
    var y2 = 0L
    var aim = 0L
    d.map { it.split(" ") }.forEach {
        when (it[0]) {
            "up" -> aim -= it[1].toInt()
            "down" -> aim += it[1].toInt()
            "forward" -> {
                x2 += it[1].toInt()
                y2 += aim * it[1].toInt()
            }
        }
    }
    println(x2 * y2)
}