import kotlin.math.abs

fun main() {
    val lines = get_input(11)

    val expandRows = ArrayList<Int>()
    val expandCols = ArrayList<Int>()

    for (y in lines.indices) {
        val line = lines[y]
        if (!line.contains("#"))
            expandRows.add(y)
    }
    for (x in lines[0].indices) {
        var needsExpanding = true
        for (y in lines.indices) {
            if (lines[y][x] == '#') {
                needsExpanding = false
                break
            }
        }
        if (needsExpanding)
            expandCols.add(x)
    }

    fun getGalaxies(): ArrayList<Pos> {
        val galaxies = ArrayList<Pos>()
        for (y in lines.indices) {
            val line = lines[y]
            for (x in line.indices) {
                if (line[x] == '#')
                    galaxies.add(Pos(x, y))
            }
        }
        return galaxies
    }
    // Adjust galaxy position (due to expansion)
    fun adjustGalaxyPosition(galaxies: ArrayList<Pos>, expansion: Int) {
        for (i in galaxies.indices) {
            val pos = galaxies[i]
            var (posX, posY) = pos

            for (y in 0..pos.second) {
                if (expandRows.contains(y)) posY += expansion - 1
            }
            for (x in 0..pos.first) {
                if (expandCols.contains(x)) posX += expansion - 1
            }

            galaxies[i] = Pos(posX, posY)
        }
    }

    fun getPathLen(from: Pos, to: Pos) = abs(to.first - from.first) + abs(to.second - from.second)
    fun sumGalaxiesPath(galaxies: ArrayList<Pos>): Long {
        var sumPath = 0L
        for (i in galaxies.indices) {
            val galaxy = galaxies[i]
            for (j in i + 1 until galaxies.size) {
                val other = galaxies[j]
                val path = getPathLen(galaxy, other)
                sumPath += path
            }
        }
        return sumPath
    }

    val galaxies = getGalaxies()
    adjustGalaxyPosition(galaxies, 2)
    println("Part 1: ${sumGalaxiesPath(galaxies)}")

    val galaxiesApart = getGalaxies()
    adjustGalaxyPosition(galaxiesApart, 1000000)
    //adjustGalaxyPosition(galaxiesApart, 100)
    println("Part 2: ${sumGalaxiesPath(galaxiesApart)}")

}