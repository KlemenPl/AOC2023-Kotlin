fun main() {
    val lines = get_input(9)
    val nums = lines.map { it.split(" ").map { it.toInt() } }

    fun getDiff(array: List<Int>) = array.zipWithNext { a, b -> b - a }

    fun findNextNum(array: List<Int>): Pair<Int, Int> {
        var diff = getDiff(array)

        val diffs = ArrayList<List<Int>>()
        diffs.add(array)

        while (diff.count { it == 0 } != diff.size) {
            diffs.add(diff)
            diff = getDiff(diff)
        }

        var accNext = 0
        var accPrev = 0
        for (d in diffs.reversed()) {
            accNext += d.last()
            accPrev = d.first() - accPrev
        }
        return Pair(accNext, accPrev)
    }

    var nextNumSum = 0
    var prevNumSum = 0
    for (n in nums) {
        val (next, prev) = findNextNum(n)

        nextNumSum += next
        prevNumSum += prev
    }

    println("Part 1: $nextNumSum")
    println("Part 2: $prevNumSum")

}