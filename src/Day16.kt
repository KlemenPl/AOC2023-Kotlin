import kotlin.math.max

fun main() {
    val input = get_input(16).map { it.toCharArray() }

    val sizeX = input[0].size
    val sizeY = input.size

    data class Beam(var x: Int, var y: Int, var dX: Int, var dY: Int)

    fun getEnergized(queue: ArrayDeque<Beam>): HashSet<Pair<Int, Int>> {
        val energized = HashSet<Pair<Int, Int>>()
        val visited = HashSet<Beam>()

        while (queue.isNotEmpty()) {
            val beam = queue.removeFirst()

            beam.x += beam.dX
            beam.y += beam.dY

            if (beam.x !in 0..<sizeX || beam.y !in 0..<sizeY)
                continue
            if (beam in visited) continue
            visited.add(beam)

            energized.add(Pair(beam.x, beam.y))

            val c = input[beam.y][beam.x]
            when (c) {
                '.' -> {
                    queue.addLast(beam)
                }
                '/' -> {
                    queue.addLast(Beam(beam.x, beam.y, -beam.dY, -beam.dX))
                }
                '\\' -> {
                    queue.addLast(Beam(beam.x, beam.y, beam.dY,  beam.dX))
                }
                '|' -> {
                    if (beam.dX == 0) {
                        queue.addLast(beam)
                    } else {
                        queue.addLast(Beam(beam.x, beam.y, 0, 1))
                        queue.addLast(Beam(beam.x, beam.y, 0, -1))
                    }
                }
                '-' -> {
                    if (beam.dY == 0) {
                        queue.addLast(beam)
                    } else {
                        queue.addLast(Beam(beam.x, beam.y, 1, 0))
                        queue.addLast(Beam(beam.x, beam.y, -1, 0))
                    }
                }
            }
        }
        return energized
    }


    val q = ArrayDeque<Beam>()
    q.add(Beam(-1, 0, 1, 0))
    println("Part 1: ${getEnergized(q).size}")

    var maxEnergized = 0

    fun simulateBean(beam: Beam) {
        q.clear()
        q.add(beam)
        maxEnergized = max(maxEnergized, getEnergized(q).size)
    }

    for (x in 0..sizeX) {
        simulateBean(Beam(x, -1, 0, 1))
        simulateBean(Beam(x, sizeY, 0, -1))
    }
    for (y in 0..sizeY) {
        simulateBean(Beam(-1, y, 1, 0))
        simulateBean(Beam(sizeX, y, -1, 0))
    }

    println("Part 2: $maxEnergized")
}