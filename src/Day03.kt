fun main() {
    val lines = get_input(3).map {
        it.toCharArray()
    }
    val numRows = lines.size
    val numCols = lines.first().size

    var sum = 0
    var gearRatioSum = 0

    for (yIdx in 0 until numRows) {
        for (xIdx in 0 until numCols) {
            val symbol = lines[yIdx][xIdx]
            if (symbol.isDigit() || symbol == '.') continue
            val adjacentNumbers = ArrayList<Int>()
            for (y in yIdx - 1 .. yIdx + 1) {
                for (x in xIdx - 1 .. xIdx + 1) {
                    if (y == yIdx && x == xIdx) continue
                    if (y < 0 || y >= numRows ||
                        x < 0 || x >= numCols)
                        continue
                    val c = lines[y][x]
                    if (!c.isDigit()) continue
                    val line = lines[y]

                    var value = c.toString()
                    var nX = x + 1
                    while (nX < numCols && line[nX].isDigit()) {
                        value += line[nX]
                        line[nX++] = '.'
                    }
                    nX = x - 1
                    while (nX >= 0 && line[nX].isDigit()) {
                        value = line[nX] + value
                        line[nX--] = '.'
                    }
                    adjacentNumbers.add(value.toInt())

                }
            }
            sum += adjacentNumbers.sum()
            if (symbol == '*' && adjacentNumbers.size == 2)
                gearRatioSum += adjacentNumbers.reduce { acc, i -> acc * i }
        }

    }

    println("Part 1: $sum")
    println("Part 2: $gearRatioSum")
}