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

        fun canBeDamaged(inputIdx: Int, numDamaged: Int): Boolean {
            if (input.length < inputIdx + numDamaged) return false
            for (i in 0..<numDamaged) {
                if (input[inputIdx + i] == '.') return false
            }
            return true
        }

        val cur = input[inputIdx]

        // Nothing to be done
        if (cur == '.') {
            val ret = getNumCombinations(input, damaged, inputIdx + 1, damagedIdx)
            memo[key] = ret
            return ret
        } else if (cur == '#') {
            if (damagedIdx >= damaged.size) return 0
            if (needsSpace) return 0
            val numDamaged = damaged[damagedIdx]
            // Invalid state
            if (!canBeDamaged(inputIdx, numDamaged)) return 0
            val newInputIdx = inputIdx + damaged[damagedIdx]
            val ret = getNumCombinations(input, damaged, newInputIdx, damagedIdx + 1, true)
            memo[key] = ret
            return ret
        }
        if (cur == '?') {
            val numOperational = getNumCombinations(input, damaged, inputIdx + 1, damagedIdx)
            if (needsSpace) {
                memo[key] = numOperational
                return numOperational
            }
            if (damagedIdx >= damaged.size) return numOperational
            if (!canBeDamaged(inputIdx, damaged[damagedIdx])) return numOperational
            val newInputIdx = inputIdx + damaged[damagedIdx]
            val numDamaged = getNumCombinations(input, damaged, newInputIdx, damagedIdx + 1, true)
            val ret = numOperational + numDamaged
            memo[key] = ret
            return ret

        }
        return 0
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