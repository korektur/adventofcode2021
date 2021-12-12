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

fun canVisitAgain(v: String): Boolean {
    for (to in graph[v]!!) {
        if (visited.getValue(to) == 0) {
            return true
        }
    }

    return false
}

//assuming there is no cycle consisting only from uppercase vertices
fun dfs(v: String, canVisitTwice: Boolean = false): Int {
    if (v == "end") {
        return 1
    }

    visited[v] = visited.getValue(v) + 1
    var ans = 0
    for (to in graph[v]!!) {
        if (visited.getValue(to) == 0 || (!to.isLowerCase() && (canVisitTwice || canVisitAgain(to)))) {
            ans += dfs(to, canVisitTwice)
        } else if (to.isLowerCase() && canVisitTwice && to != "start") {
            ans += dfs(to, false)
        }
    }
    visited[v] = visited.getValue(v) - 1
    return ans
}

println(dfs("start"))
visited.clear()
println(dfs("start", true))
