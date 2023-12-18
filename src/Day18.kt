import kotlin.math.abs

fun main() {
    val input = get_input(18)

    fun getPoints(points: MutableList<Pair<Long, Long>>, parser:(String) -> Pair<String, Long>): Long {
        points.add(Pair(0, 0))
        var posX = 0L
        var posY = 0L
        var boundaryPoints = 0L
        for (line in input) {
            val (dir, amount) = parser(line)
            val (dX, dY) = when (dir) {
                "U" -> Pair(0, -1)
                "D" -> Pair(0, 1)
                "L" -> Pair(-1, 0)
                "R" -> Pair(1, 0)
                else -> error("")
            }
            posX += dX * amount
            posY += dY * amount
            val point = Pair(posX, posY)
            points.add(point)
            boundaryPoints += amount
        }
        return boundaryPoints
    }

    fun decodeHex(hex: String): Pair<String, Long> {
        // Remove (# )
        val hex = hex.substring(2, hex.length - 1)
        val dst = hex.substring(0, hex.length - 1).toLong(16)
        val dirIdx = hex.substring(hex.length - 1).toInt(16)
        val dir = listOf("R", "D", "L", "U")[dirIdx]
        return Pair(dir, dst)
    }

    fun calculateArea(points: List<Pair<Long, Long>>, boundaryPoints: Long): Long {
        // shoelace
        var area = 0L
        for (i in points.indices) {
            val prevIdx = (points.size + i - 1) % points.size
            val nextIdx = (i + 1) % points.size

            area += points[i].first * (points[prevIdx].second - points[nextIdx].second)
        }
        area = abs(area) / 2
        // Pick's theorem
        area = area - boundaryPoints / 2 + 1
        area += boundaryPoints
        return area
    }


    val points = ArrayList<Pair<Long, Long>>()
    var boundaryPoints = getPoints(points) { line ->
        val (dir, amount) = line.split(" ")
        Pair(dir, amount.toLong())
    }
    val areaP1 = calculateArea(points, boundaryPoints)
    println("Part 1: $areaP1")

    points.clear()
    boundaryPoints = getPoints(points) { line -> decodeHex(line.split(" ")[2]) }
    val areaP2 = calculateArea(points, boundaryPoints)
    println("Part 2: $areaP2")

}