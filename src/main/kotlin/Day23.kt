import java.io.File
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


val cost = mapOf(0 to 1, 1 to 10, 2 to 100, 3 to 1000)
val roomCoordinates = IntArray(4)
var curMinScore = Long.MAX_VALUE

fun isDone(rooms: Array<IntArray>): Boolean {
    for (i in rooms.indices) {
        if (rooms[i].any { it != i }) {
            return false
        }
    }
    return true
}

fun solve(score: Long, rooms: Array<IntArray>, hall: IntArray): Long {
    if (isDone(rooms)) {
        return score
    }
    var ans = Long.MAX_VALUE

    if (score > curMinScore) {
        return Long.MAX_VALUE
    }

    for (i in hall.indices) {
        if (hall[i] == -1) {
            continue
        }

        val room = rooms[hall[i]]
        if (room.any { it != -1 && it != hall[i] }) {
            continue
        }
        val path = (min(i, roomCoordinates[hall[i]])..max(i, roomCoordinates[hall[i]])).minus(i)
        var isPossible = true
        for (j in path) {
            if (hall[j] != -1) {
                isPossible = false
                break
            }
        }

        if (isPossible) {
            var pos = 0
            while(pos < room.size && room[pos] != -1) pos++
            if (pos == room.size) {
                println("HERE")
            }
            val newScore = score + (abs(i - roomCoordinates[hall[i]]) + (pos + 1)) * cost[hall[i]]!!
            room[pos] = hall[i]
            hall[i] = -1
            ans = solve(newScore, rooms, hall)
            hall[i] = room[pos]
            room[pos] = -1
            return ans
        }
    }

    for (i in rooms.indices) {
        val room = rooms[i]
        if (room.all { it == i || it == -1 }) {
            continue
        }

        var j = 0
        while (room[j] == -1) {
            j++
        }

        val h = room[j]
        room[j] = -1
        val roomCoords = roomCoordinates[i]
        for (x in roomCoords downTo 0) {
            if (hall[x] != -1) {
                break
            }

            if (roomCoordinates.any { it == x }) {
                continue
            }

            hall[x] = h
            val t = score + (j + 1 + abs(roomCoords - x)) * cost[h]!!
            ans = min(ans, solve(t, rooms, hall))
            hall[x] = -1
        }

        for (x in roomCoords until hall.size) {
            if (hall[x] != -1) {
                break
            }

            if (roomCoordinates.any { it == x }) {
                continue
            }

            hall[x] = h
            val t = score + (j + 1 + abs(x - roomCoords)) * cost[h]!!
            ans = min(ans, solve(t, rooms, hall))
            hall[x] = -1
        }
        room[j] = h
    }

    curMinScore = min(curMinScore, ans)
    return ans
}

fun main() {
    val rooms = Array(4) { IntArray(4) }
    val input = File("src/main/resources/day23_2.in").readLines()
    val hallSize = input[1].count { it == '.' }
    var hall = IntArray(hallSize) { -1 }

    var roomId = 0
    for (i in input[2].indices) {
        val c = input[2][i]
        if (c != '#') {
            val room = rooms[roomId++]
            room[0] = (c - 'A')
            room[1] = (input[3][i] - 'A')
            room[2] = (input[4][i] - 'A')
            room[3] = (input[5][i] - 'A')
            roomCoordinates[roomId - 1] = i - 1
        }
    }

    println(solve(0L, rooms, hall))
}

