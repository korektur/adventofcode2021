import java.io.File
import java.io.FileInputStream
import java.util.Scanner

val f = File("src/main/resources/day4.in")
val input = Scanner(FileInputStream(f))
input.use {

    val nums = input.nextLine().split(",").map { it.toInt() }.toList()
    var maxMoves = 0
    var ans = 0

    while (input.hasNext()) {
        val rows = ArrayList<MutableSet<Int>>(5)
        val columns = ArrayList<MutableSet<Int>>(5)
        for (i in 1..5) {
            rows.add(HashSet())
            columns.add(HashSet())
        }
        for (i in 1..5) {
            for (j in 1..5) {
                val x = input.nextInt()
                rows[i - 1].add(x)
                columns[j - 1].add(x)
            }
        }

        for (i in nums.indices) {
            rows.forEach { it.remove(nums[i]) }
            columns.forEach { it.remove(nums[i]) }
            if (rows.any { it.size == 0 } || columns.any { it.size == 0 }) {
                if (i >= maxMoves) {
                    ans = rows.flatten().sum() * nums[i]
                    maxMoves = i + 1
                }
                break
            }
        }
    }

    println(ans)
}

