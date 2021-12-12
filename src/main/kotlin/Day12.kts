import java.io.File

val graph = HashMap<String, MutableSet<String>>().withDefault { mutableSetOf() }
val visited = mutableMapOf<String, Int>().withDefault { 0 }
File("src/main/resources/day12.in").forEachLine { line ->
    val (from, to) = line.split("-")
    graph.computeIfAbsent(from) { mutableSetOf() }.add(to)
    graph.computeIfAbsent(to) { mutableSetOf() }.add(from)
}

fun String.isLowerCase(): Boolean {
    return this.lowercase() == this
}

fun canVisitAgain(v: String, canVisitTwice: Boolean): Boolean {
    return canVisitTwice || graph[v]!!.any { visited.getValue(it) == 0 }
}

//assuming there is no cycle consisting only from uppercase vertices
fun dfs(v: String, canVisitTwice: Boolean = false): Int {
    if (v == "end") {
        return 1
    }

    visited[v] = visited.getValue(v) + 1
    var ans = 0
    for (to in graph[v]!!) {
        val isLowerCase = to.isLowerCase()
        if (visited.getValue(to) == 0 || (!isLowerCase && canVisitAgain(to, canVisitTwice))) {
            ans += dfs(to, canVisitTwice)
        } else if (isLowerCase && canVisitTwice && to != "start") {
            ans += dfs(to, false)
        }
    }
    visited[v] = visited.getValue(v) - 1
    return ans
}

println(dfs("start"))
visited.clear()
println(dfs("start", true))
