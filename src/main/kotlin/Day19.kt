import java.io.File
import kotlin.math.abs
import kotlin.math.max

data class Scanner(
    val id: Int,
    val beacons: MutableList<MutableList<Int>>,
    val coords: IntArray = intArrayOf(0, 0, 0)
) {

}

val beacons = mutableSetOf<MutableList<Int>>()

fun areVisible(fst: Scanner, snd: Scanner): Boolean {
    for (xI in 0..2) {
        for (xSign in sequenceOf(-1, 1)) {
            for (yI in (0..2).minus(xI)) {
                for (ySign in sequenceOf(-1, 1)) {
                    val zI = (0..2).minus(xI).minus(yI)[0]
                    for (zSign in sequenceOf(-1, 1)) {
                        for (beacon1 in fst.beacons.indices) {
                            for (beacon2 in snd.beacons.indices) {
                                val x = fst.beacons[beacon1][0] - snd.beacons[beacon2][xI] * xSign
                                val y = fst.beacons[beacon1][1] - snd.beacons[beacon2][yI] * ySign
                                val z = fst.beacons[beacon1][2] - snd.beacons[beacon2][zI] * zSign
                                var cnt = 0

                                for (beacon in snd.beacons) {
                                    val bx = x + beacon[xI] * xSign
                                    val by = y + beacon[yI] * ySign
                                    val bz = z + beacon[zI] * zSign
                                    if (fst.beacons.contains(
                                            mutableListOf(
                                                bx,
                                                by,
                                                bz
                                            )
                                        )
                                    ) {
                                        cnt++
                                    }
                                }
                                if (cnt >= 12) {
                                    snd.coords[0] = x
                                    snd.coords[1] = y
                                    snd.coords[2] = z
                                    for (i in snd.beacons.indices) {
                                        val cur = snd.beacons[i]
                                        snd.beacons[i] = mutableListOf(cur[xI] * xSign + x, cur[yI] * ySign + y, cur[zI] * zSign + z)
                                        if (!beacons.contains(snd.beacons[i])) {
                                            beacons.add(snd.beacons[i])
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
    val unknownScanners = mutableSetOf<Int>()
    reader.use {
        var i = 0
        while (reader.ready()) {
            val scanner = Scanner(i++, mutableListOf())
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