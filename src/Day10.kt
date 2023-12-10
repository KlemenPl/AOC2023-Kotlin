import kotlin.math.max

typealias Pos = Pair<Int, Int>

fun main() {
    val lines = get_input(10)

    val graph = HashMap<Pos, List<Pos>>()

    var startPos = Pos(0, 0)

    for (y in lines.indices) {
        val line = lines[y]
        for (x in line.indices) {
            val c = line[x]
            if (c == 'S') {
                startPos = Pair(x, y)
            }
            val pos = Pos(x, y)
            val UP = Pos(x, y - 1)
            val DOWN = Pos(x, y + 1)
            val LEFT = Pos(x - 1, y)
            val RIGHT = Pos(x + 1, y)
            graph[pos] = when (c) {
                '|' -> listOf(UP, DOWN)
                '-' -> listOf(LEFT, RIGHT)
                'L' -> listOf(UP, RIGHT)
                'J' -> listOf(UP, LEFT)
                '7' -> listOf(LEFT, DOWN)
                'F' -> listOf(RIGHT, DOWN)
                'S' -> listOf(UP, LEFT, DOWN, RIGHT)
                else -> listOf()
            }

        }
    }

    val sizeX = lines.first().length
    val sizeY = lines.size


    var maxDst = -1
    var mainLoop = HashSet<Pos>()

    for (pos in graph[startPos]!!) {
        var prev = startPos
        var cur = pos
        var dst = 0

        val currLoop = HashSet<Pos>()
        while (cur != startPos) {
            if (graph[cur] == null) break
            val new = graph[cur]!!.filter { it != prev }
            if (new.isEmpty()) break
            assert(new.size == 1)

            prev = cur
            cur = new.first()
            dst++
            currLoop.add(cur)
        }

        dst = dst / 2 + 1
        if (cur == startPos && dst > maxDst) {
            maxDst = dst
            mainLoop = currLoop
        }
    }

    println("Part 1: $maxDst")

    var enclosedCount = 0
    for (yIdx in lines.indices) {
        val line = lines[yIdx]
        for (xIdx in line.indices) {
            val start = Pos(xIdx, yIdx)
            if (mainLoop.contains(start)) continue

            fun countLoopTiles(startX: Int, startY: Int, dX: Int, dY: Int): Int {
                var x = startX
                var y = startY
                var count = 0

                while (x in 0 until sizeX && y in 0 until sizeY) {
                    val pos = Pos(x, y)
                    val tile = lines[y][x]
                    if (mainLoop.contains(pos) && "|JLS".contains(tile)) count++

                    x += dX
                    y += dY
                }

                return count
            }
            val (x, y) = start
            val isEnclosed = countLoopTiles(x, y, -1, 0) % 2 == 1
            if (isEnclosed) enclosedCount++


            // Flood fill
            /*
            val q = ArrayDeque<Pos>()
            q.addLast(start)

            val visited = HashSet<Pos>()
            var isEnclosed = true
            if (start == Pair(3, 2)) {
                println("yi")
            }
            while (q.isNotEmpty()) {
                val curPos = q.removeFirst()
                val (x, y) = curPos
                // Touching boundary?
                if (x < 0 || x >= sizeX || y < 0 || y >= sizeY) {
                    isEnclosed = false
                    break
                }
                if (mainLoop.contains(curPos)) {
                }
                // Touching non-loop pipes
                //val tile = lines[y][x]
                //if (tile != '.' && !mainLoop.contains(curPos)) {
                //    isEnclosed = false
                //    break
                //} else if (tile != '.') {
                //    //continue
                //}
                visited.add(curPos)

                for (dY in -1..1) {
                    for (dX in -1..1) {
                        val newX = x + dX
                        val newY = y + dY
                        val newPos = Pos(newX, newY)
                        if (visited.contains(newPos)) continue
                        if (mainLoop.contains(newPos)) continue
                        q.addLast(newPos)
                    }
                }
            }

            if (isEnclosed) {
                // We found enclosed nest
                println(visited)
                println(q.isEmpty())
                println(isEnclosed)
                enclosed.addAll(visited)

            }
             */

        }
    }

    println("Part 2: $enclosedCount")
}
