import java.io.File
import kotlin.math.abs
import kotlin.math.max

data class Scanner(
    val id: Int,
    var beacons: MutableSet<List<Int>>,
    @Suppress("ArrayInDataClass") val coords: IntArray = intArrayOf(0, 0, 0)
)

val beacons = mutableSetOf<List<Int>>()

fun areVisible(fst: Scanner, snd: Scanner): Boolean {
    for (xI in 0..2) {
        for (xSign in sequenceOf(-1, 1)) {
            for (yI in (0..2).minus(xI)) {
                for (ySign in sequenceOf(-1, 1)) {
                    val zI = (0..2).minus(xI).minus(yI)[0]
                    for (zSign in sequenceOf(-1, 1)) {
                        for (beacon1 in fst.beacons) {
                            for (beacon2 in snd.beacons) {
                                val x = beacon1[0] - beacon2[xI] * xSign
                                val y = beacon1[1] - beacon2[yI] * ySign
                                val z = beacon1[2] - beacon2[zI] * zSign
                                var cnt = 0

                                for (beacon in snd.beacons) {
                                    val bx = x + beacon[xI] * xSign
                                    val by = y + beacon[yI] * ySign
                                    val bz = z + beacon[zI] * zSign
                                    if (fst.beacons.contains(listOf(bx, by, bz))) {
                                        if (++cnt >= 12) {
                                            break
                                        }
                                    }
                                }
                                if (cnt >= 12) {
                                    snd.coords[0] = x
                                    snd.coords[1] = y
                                    snd.coords[2] = z
                                    val newBeacons = snd.beacons
                                        .map {
                                            listOf(it[xI] * xSign + x, it[yI] * ySign + y, it[zI] * zSign + z)
                                        }
                                        .toMutableSet()
                                    snd.beacons = newBeacons
                                    newBeacons.forEach { beacons.add(it) }

                                    return true
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    return false
}


fun main() {
    val scanners = mutableListOf<Scanner>()
    val reader = File("src/main/resources/day19.in").bufferedReader()
    val unknownScanners = mutableSetOf<Int>()
    reader.use {
        var i = 0
        while (reader.ready()) {
            val scanner = Scanner(i++, mutableSetOf())
            reader.readLine()
            var line = reader.readLine()
            while (line != null && line.isNotBlank()) {
                scanner.beacons.add(line.split(",").map { it.toInt() }.toMutableList())
                line = reader.readLine()
            }
            scanners.add(scanner)
            unknownScanners.add(scanner.id)
        }
    }

    for (beacon in scanners[0].beacons) {
        beacons.add(beacon)
    }

    val q = ArrayDeque<Int>()
    q.add(0)
    unknownScanners.remove(0)
    while (q.isNotEmpty()) {
        val i = q.removeFirst()

        for (j in unknownScanners.toIntArray()) {
            if (areVisible(scanners[i], scanners[j])) {
                q.addLast(j)
                unknownScanners.remove(scanners[j].id)
            }
        }
    }

    println(beacons.size)

    var maxDist = 0
    for (i in scanners.indices) {
        for (j in i + 1 until scanners.size) {
            val dist =
                abs(scanners[i].coords[0] - scanners[j].coords[0]) +
                        abs(scanners[i].coords[1] - scanners[j].coords[1]) +
                        abs(scanners[i].coords[2] - scanners[j].coords[2])
            maxDist = max(maxDist, dist)
        }
    }

    println(maxDist)
}