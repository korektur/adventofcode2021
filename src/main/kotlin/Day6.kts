import java.io.File

val birthdays = File("src/main/resources/day6.in").readLines()[0]
    .split(",")
    .groupingBy { it.toInt() }
    .eachCount()
    .mapValues { it.value.toLong() }
    .toMutableMap()

var ans = 0L
birthdays.values.forEach{ ans += it }

for(i in 1 until 256) {
    val birthdaysToday = birthdays.getOrElse(i) { 0 }
    ans += birthdaysToday
    birthdays.merge(i + 7, birthdaysToday, Long::plus)
    birthdays.merge(i + 9, birthdaysToday, Long::plus)
}

println(ans)