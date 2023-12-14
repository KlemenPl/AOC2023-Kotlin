import java.util.HashMap

fun main() {
    val lines = get_input(14)
    var input = lines.map { it.toMutableList() }.toMutableList()

    fun tilt(input: MutableList<MutableList<Char>>, dir: Pair<Int, Int>) {
        val (dirX, dirY) = dir
        val yRange = if (dirY > 0) input.indices.reversed() else input.indices
        val xRange = if (dirX > 0) input[0].indices.reversed() else input[0].indices
        for (yIdx in yRange) {
            val row = input[yIdx]
            for (xIdx in xRange) {
                var x = xIdx
                var y = yIdx
                while (x >= -dirX && x < row.size - dirX &&
                       y >= -dirY && y < input.size - dirY) {
                    val nextX = x + dirX
                    val nextY = y + dirY
                    if (input[y][x] == 'O' && input[nextY][nextX] == '.') {
                        input[y][x] = '.'
                        input[nextY][nextX] = 'O'
                    }
                    x = nextX
                    y = nextY
                }
            }
        }
    }

    fun calculateLoad(input: List<List<Char>>): Long {
        var load = 0L
        for (y in input.indices) {
            val row = input[y]
            for (x in row.indices) {
                if (row[x] == 'O') load += input.size - y
            }
        }
        return load
    }

    val NORTH = Pair(0, -1)
    val SOUTH = Pair(0, 1)
    val EAST  = Pair(1, 0)
    val WEST  = Pair(-1, 0)

    tilt(input, NORTH)
    println("Part 1: ${calculateLoad(input)}")

    input = lines.map { it.toMutableList() }.toMutableList()
    fun cycle(inp: MutableList<MutableList<Char>>) {
        val dirs = listOf(NORTH, WEST, SOUTH, EAST)
        for (dir in dirs)
            tilt(inp, dir)
    }

    val inputHashes = HashMap<Int, Int>()
    var iter = 1
    val numCycles = 1000000000
    while (iter < numCycles) {
        cycle(input)
        iter++
        val hash = input.hashCode()
        if (hash in inputHashes) {
            val step = iter - inputHashes[hash]!!
            //while (iter < numCycles) iter += step
            //iter -= step
            iter += ((numCycles - iter) / step) * step
            for (i in iter..numCycles) {
                cycle(input)
            }
            println("Part 2: ${calculateLoad(input)}")
            break
        }
        inputHashes[hash] = iter
    }
}