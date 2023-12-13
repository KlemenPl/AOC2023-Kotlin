fun main() {
    val input = get_input(13).joinToString("\n")
    val patterns = input.split("\n\n").map { it.split("\n") }

    fun hasVerticalReflection(lines: List<String>, idx: Int): Boolean {
        var l = idx
        var r = idx + 1

        while (l >= 0 && r < lines[0].length) {

            for (line in lines) {
                if (line[l] != line[r])
                    return false
            }
            l--
            r++
        }
        return true
    }

    fun hasHorizontalReflection(lines: List<String>, idx: Int): Boolean {
        var l = idx
        var r = idx + 1

        while (l >= 0 && r < lines.size) {
            if (lines[l] != lines[r])
                return false

            l--
            r++
        }
        return true
    }

    fun getHorizontalReflections(pattern: List<String>, cb: ((row :Int) -> Unit)) {
        for (y in 0..<pattern.size - 1) {
            if (hasHorizontalReflection(pattern, y))
                cb(y)
        }
    }
    fun getVerticalReflections(pattern: List<String>, cb: ((col: Int) -> Unit)) {
        for (x in 0..<pattern[0].length - 1) {
            if (hasVerticalReflection(pattern, x))
                cb(x)
        }
    }

    fun unsmudge(pattern: List<String>, vertical: HashSet<Int>, horizontal: HashSet<Int>): List<String> {
        val newPattern = pattern.toMutableList().map { it.toCharArray() }

        fun flip(c: Char) = if (c == '#') '.' else '#'

        for (y in newPattern.indices) {
            for (x in newPattern[y].indices) {
                newPattern[y][x] = flip(newPattern[y][x])
                val pattern = newPattern.map { it.joinToString("") }

                var foundUnsmudged = false
                getVerticalReflections(pattern) { xIdx ->
                    if (!vertical.contains(xIdx)) {
                        foundUnsmudged = true
                        return@getVerticalReflections
                    }
                }
                if (foundUnsmudged) return pattern
                getHorizontalReflections(pattern) { yIdx ->
                    if (!horizontal.contains(yIdx)) {
                        foundUnsmudged = true
                        return@getHorizontalReflections
                    }
                }
                if (foundUnsmudged) return pattern

                newPattern[y][x] = flip(newPattern[y][x])
            }
        }

        assert(false)
        return newPattern.map { it.joinToString("") }
    }

    var numReflections = 0L
    var numReflectionsUnsmudged = 0L
    for (pattern in patterns) {
        val vertical = HashSet<Int>()
        val horizontal = HashSet<Int>()
        getVerticalReflections(pattern) { x->
            numReflections += (x + 1)
            vertical.add(x)
        }
        getHorizontalReflections(pattern) { y->
            numReflections += (y + 1) * 100
            horizontal.add(y)
        }

        val unsmudged = unsmudge(pattern, vertical, horizontal)
        getVerticalReflections(unsmudged) { x ->
            if (!vertical.contains(x)) numReflectionsUnsmudged += (x + 1)
        }
        getHorizontalReflections(unsmudged) { y->
            if (!horizontal.contains(y)) numReflectionsUnsmudged += (y + 1) * 100
        }
    }

    println("Part 1: $numReflections")
    println("Part 2: $numReflectionsUnsmudged")
}