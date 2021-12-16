import java.io.File
import java.util.function.BinaryOperator

val map = mapOf(
    '0' to "0000",
    '1' to "0001",
    '2' to "0010",
    '3' to "0011",
    '4' to "0100",
    '5' to "0101",
    '6' to "0110",
    '7' to "0111",
    '8' to "1000",
    '9' to "1001",
    'A' to "1010",
    'B' to "1011",
    'C' to "1100",
    'D' to "1101",
    'E' to "1110",
    'F' to "1111"
)
val opMap = mapOf(
    0 to BinaryOperator(Long::plus),
    1 to BinaryOperator(Long::times),
    2 to BinaryOperator(Math::min),
    3 to BinaryOperator(Math::max),
    5 to BinaryOperator { x, y -> if (x > y) 1L else 0L },
    6 to BinaryOperator { x, y -> if (x < y) 1L else 0L },
    7 to BinaryOperator { x, y -> if (x == y) 1L else 0L },
)

val input = File("src/main/resources/day16.in").readLines()[0]
    .map { map[it] }
    .joinToString(separator = "")

var pos = 0
var ans1 = 0

fun parseLiteral(line: String): Long {
    var value = 0L
    while (true) {
        value = value * 16 + Integer.parseInt(line, pos + 1, pos + 5, 2)
        pos += 5
        if (line[pos - 5] == '0') break
    }

    return value
}

fun parseOperator(line: String, op: BinaryOperator<Long>): Long {
    val lenBit = line[pos++]
    val nums = mutableListOf<Long>()
    if (lenBit == '0') {
        val end = pos + 15 + Integer.parseInt(line, pos, pos + 15, 2)
        pos += 15
        while (pos < end) {
            nums.add(parse(line))
        }
    } else {
        val cnt = Integer.parseInt(line, pos, pos + 11, 2)
        pos += 11
        for (i in 1..cnt) {
            nums.add(parse(line))
        }
    }

    return nums.reduce { x, y -> op.apply(x, y) }
}

fun parse(line: String): Long {
    ans1 += Integer.parseInt(line, pos, pos + 3, 2)
    val type = Integer.parseInt(line, pos + 3, pos + 6, 2)
    pos += 6
    return if (type == 4) {
        parseLiteral(line)
    } else {
        parseOperator(line, opMap[type]!!)
    }
}

println(parse(input))
println(ans1)