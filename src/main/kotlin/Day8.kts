import java.io.File

val ans1 = File("src/main/resources/day8.in").bufferedReader()
    .useLines { it ->
        it.map { it.split("|")[1] }
            .map { it.split(" ") }
            .flatten()
            .filter { it.length == 2 || it.length == 3 || it.length == 4 || it.length == 7 }
            .count()
    }

println(ans1)

//part2
var ans2 = 0
File("src/main/resources/day8.in").forEachLine { line ->
    val mapping = HashMap<Int, String>()

    var (input, output) = line.split(" | ").map { it.split(" ") }
    input = input.map { it.toCharArray().apply { sort() }.concatToString() }
    output = output.map { it.toCharArray().apply { sort() }.concatToString() }

    val sortedInput = input.sortedBy { it.length }
    mapping[1] = sortedInput[0]
    mapping[7] = sortedInput[1]
    mapping[4] = sortedInput[2]
    mapping[8] = sortedInput.last()
    mapping[6] = input.last { it.length == 6 && !(it.contains(sortedInput[0][0]) && it.contains(sortedInput[0][1])) }
    mapping[5] = input.last { it.length == 5 && it.all { c -> mapping[6]!!.contains(c) } }
    mapping[9] = input.last { it.length == 6 && !mapping[6].equals(it) && mapping[5]!!.all { c -> it.contains(c) } }
    mapping[0] = input.last { it.length == 6 && !mapping[6].equals(it) && !mapping[9].equals(it) }
    mapping[3] = input.last { it.length == 5 && !mapping[5].equals(it) && it.all { c -> mapping[9]!!.contains(c) } }
    mapping[2] = input.last { it.length == 5 && !mapping[5].equals(it) && !mapping[3].equals(it) }

    val nums = mapping.entries.associate { (k, v) -> v to k }
    var curAns = 0
    output.forEach { v ->
        curAns *= 10
        curAns += nums[v]!!
    }

    ans2 += curAns
}
println(ans2)

