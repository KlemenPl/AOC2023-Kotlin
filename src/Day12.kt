import java.math.BigInteger

fun main() {
    val lines = get_input(12)

    data class MemoKey(val inputIdx: Int, val damagedIdx: Int, var needsSpace: Boolean)

    val memo = HashMap<MemoKey, Long>()

    fun getNumCombinations(input: String, damaged: List<Int>,
                           inputIdx: Int, damagedIdx: Int,
                           needsSpace: Boolean = false): Long {
        val key = MemoKey(inputIdx, damagedIdx, needsSpace)
        if (memo.contains(key)) {
            return memo[key]!!
        }
        if (inputIdx >= input.length && damagedIdx >= damaged.size)
            return 1
        if (inputIdx >= input.length)
            return 0

        fun canBeDamaged(): Boolean {
            val numDamaged = damaged[damagedIdx]
            if (input.length < inputIdx + numDamaged) return false
            for (i in 0..<numDamaged) {
                if (input[inputIdx + i] == '.') return false
            }
            return true
        }
        fun takeGroup() = getNumCombinations(input, damaged, inputIdx + damaged[damagedIdx], damagedIdx + 1, true)

        val cur = input[inputIdx]
        var ret = 0L

        // Nothing to be done
        if (cur == '.') {
            ret = getNumCombinations(input, damaged, inputIdx + 1, damagedIdx)
        }
        if (cur == '#') {
            // Invalid state
            if (damagedIdx >= damaged.size || needsSpace || !canBeDamaged())
                return 0
            ret = takeGroup()
        }
        if (cur == '?') {
            val numOperational = getNumCombinations(input, damaged, inputIdx + 1, damagedIdx)
            if (needsSpace || damagedIdx >= damaged.size || !canBeDamaged())
                ret = numOperational
            else
                ret = numOperational + takeGroup()
        }
        memo[key] = ret
        return ret
    }

    fun unfoldSpring(spring: String, n: Int): String {
        val sb = StringBuilder()

        sb.append(spring)
        for (i in 1..<n) {
            sb.append('?').append(spring)
        }

        return sb.toString()
    }
    fun unfoldDamaged(damaged: MutableList<Int>, n: Int): MutableList<Int> {
        val unfolded = ArrayList<Int>()
        for (i in 0..<n) {
            unfolded.addAll(damaged)
        }
        return unfolded
    }

    var totalCombinations = 0L
    var totalCombinationsUnfolded = 0L
    for (line in lines) {
        val (spring, broken) = line.split(" ")
        val damagedArray = broken.split(",").map { it.toInt() }.toMutableList()

        memo.clear()
        totalCombinations += getNumCombinations(spring, damagedArray, 0, 0)

        val unfoldFactor = 5
        val unfoldedSpring = unfoldSpring(spring, unfoldFactor)
        val unfoldedDamaged = unfoldDamaged(damagedArray, unfoldFactor)
        memo.clear()
        totalCombinationsUnfolded += getNumCombinations(unfoldedSpring, unfoldedDamaged, 0, 0)
    }
    println("Part 1: $totalCombinations")
    println("Part 2: $totalCombinationsUnfolded")
}