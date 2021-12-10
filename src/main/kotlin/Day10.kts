import java.io.File
import java.util.Stack

val stack = Stack<Char>()
var ans = 0
val corruptedScores = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
val incompleteScores = mapOf('(' to 1L, '[' to 2L, '{' to 3L, '<' to 4L)
val scoreboard = arrayListOf<Long>()
File("src/main/resources/day10.in").forEachLine { line ->
    for (c in line) {
        if (c == '{' || c == '[' || c == '(' || c == '<') {
            stack.push(c)
        } else {
            if (stack.isEmpty()) {
                break
            }

            val opening = stack.pop()
            if ((opening == '{' && c != '}') || (opening == '(' && c != ')') ||
                (opening == '[' && c != ']') || (opening == '<' && c != '>')) {
                ans += corruptedScores[c]!!
                stack.clear()
                break
            }
        }
    }

    var curScore = 0L
    while(!stack.isEmpty()) {
        curScore = curScore * 5L + incompleteScores[stack.pop()]!!
    }

    if (curScore > 0) {
        scoreboard.add(curScore)
    }
}

scoreboard.sort()

println(ans)
println(scoreboard[scoreboard.size / 2])