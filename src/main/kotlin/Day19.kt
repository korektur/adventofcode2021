import java.io.File
import java.lang.Math.abs
import java.lang.Math.max

data class Scanner(val id: Int, val beacons: MutableList<IntArray>, val coords: IntArray = intArrayOf(0, 0, 0)) {

}

val beacons = mutableSetOf<IntArray>()

fun areVisible(fst: Scanner, snd: Scanner): Boolean {
    for (xI in 0..2) {
        for (xSign in sequenceOf(-1, 1)) {
            for (yI in (0..2).minus(xI)) {
                for (ySign in sequenceOf(-1, 1)) {
                    val zI = (0..2).minus(xI).minus(yI)[0]
                    for (zSign in sequenceOf(-1, 1)) {
                        for (beacon1 in fst.beacons.indices) {
                            for (beacon2 in snd.beacons.indices) {
                                val x = fst.coords[0] + fst.beacons[beacon1][0] - snd.beacons[beacon2][xI] * xSign
                                val y = fst.coords[1] + fst.beacons[beacon1][1] - snd.beacons[beacon2][yI] * ySign
                                val z = fst.coords[2] + fst.beacons[beacon1][2] - snd.beacons[beacon2][zI] * zSign
                                var cnt = 0

                                for (beacon in snd.beacons) {
                                    val bx = x + beacon[xI] * xSign
                                    val by = y + beacon[yI] * ySign
                                    val bz = z + beacon[zI] * zSign
                                    if (fst.beacons.any {
                                            it.contentEquals(
                                                intArrayOf(bx - fst.coords[0], by - fst.coords[1], bz - fst.coords[2])
                                            )
                                        }) {
                                        cnt++
                                    }
                                }
                                if (cnt >= 12) {
                                    snd.coords[0] = x
                                    snd.coords[1] = y
                                    snd.coords[2] = z
                                    for (i in snd.beacons.indices) {
                                        val cur = snd.beacons[i]
                                        snd.beacons[i] = intArrayOf(cur[xI] * xSign, cur[yI] * ySign, cur[zI] * zSign)
                                        val realCoords = intArrayOf(
                                            snd.beacons[i][0] + x,
                                            snd.beacons[i][1] + y,
                                            snd.beacons[i][2] + z
                                        )
                                        if (!beacons.any { it.contentEquals(realCoords) }) {
                                            beacons.add(realCoords)
                                        }
                                    }
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
    reader.use {
        var i = 0
        while (reader.ready()) {
            val scanner = Scanner(i++, mutableListOf())
            reader.readLine()
            var line = reader.readLine()
            while (line != null && line.isNotBlank()) {
                scanner.beacons.add(line.split(",").map { it.toInt() }.toIntArray())
                line = reader.readLine()
            }
            scanners.add(scanner)
        }
    }

    for (beacon in scanners[0].beacons) {
        beacons.add(beacon)
    }

    while (true) {
        val before = beacons.size
        for (i in scanners.indices) {
            if (i > 0 && scanners[i].coords.contentEquals(intArrayOf(0, 0, 0))) {
                continue
            }
            for (j in scanners.indices.minus(i)) {
                if (!scanners[j].coords.contentEquals(intArrayOf(0, 0, 0))) {
                    continue
                }
                areVisible(scanners[i], scanners[j])
            }
        }
        if (before == beacons.size) {
            break
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