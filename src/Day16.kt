fun main() {
    val input = get_input(0).map { it.toCharArray() }

    val sizeX = input[0].size
    val sizeY = input.size

    data class Beam(var x: Int, var y: Int, var dX: Int, var dY: Int)

    val energized = HashSet<Pair<Int, Int>>()
    val q = ArrayDeque<Beam>()
    q.add(Beam(-1, 0, 1, 0))
    energized.add(Pair(0, 0))
    val visited = HashSet<Beam>()

    while (q.isNotEmpty()) {
        val beam = q.removeFirst()

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
                q.addLast(beam)
            }
            '/' -> {
                q.addLast(Beam(beam.x, beam.y, -beam.dY, -beam.dX))
            }
            '\\' -> {
                q.addLast(Beam(beam.x, beam.y, beam.dY,  beam.dX))
            }
            '|' -> {
                if (beam.dX == 0) {
                    q.addLast(beam)
                } else {
                    q.addLast(Beam(beam.x, beam.y, 0, 1))
                    q.addLast(Beam(beam.x, beam.y, 0, -1))
                }
            }
            '-' -> {
                if (beam.dY == 0) {
                    q.addLast(beam)
                } else {
                    q.addLast(Beam(beam.x, beam.y, 1, 0))
                    q.addLast(Beam(beam.x, beam.y, -1, 0))
                }
            }
        }
    }
    for (y in input.indices) {
        val line = input[y]
        for (x in line.indices) {
            if (Pair(x, y) in energized)
                print("#")
            else
                print(line[x])
        }
        println()
    }


    println("Part 1: ${energized.size}")
}