fun main() {
    val lines = get_input(1)
    println(lines)

    var sum = 0
    for (line in lines) {
        val digits = line.replace("[a-zA-Z]+".toRegex(), "")
        val nums = digits.first().toString() + digits.last()

        val value = nums.toInt()
        sum += value
    }

    println("Part 1: $sum")

    val numberStrings = arrayOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    val numberReplacement = (1..9).toList().map { it.toString() }

    sum = 0
    for (line in lines) {
        var minIdx = -1
        var maxIdx = -1
        var min = line.length
        var max = -1
        for (i in numberStrings.indices) {
            val str = numberStrings[i]
            val oc = line.indexOf(str)
            val ocLast = line.lastIndexOf(str)
            if (oc != -1 && oc < min) {
                min = oc
                minIdx = i
            }
            if (ocLast != -1 && ocLast > max) {
                max = ocLast
                maxIdx = i
            }

        }
        var digits = line
        var firstIdx = -1
        var lastIdx = -1
        if (minIdx != -1)
            firstIdx = digits.indexOf(numberStrings[minIdx])
        if (maxIdx != -1)
            lastIdx = digits.lastIndexOf(numberStrings[maxIdx])

        if (firstIdx != -1) {
            digits = digits.substring(0, firstIdx) + numberReplacement[minIdx] + digits.substring(firstIdx)
        }
        if (lastIdx != -1) {
            val len = numberStrings[maxIdx].length
            digits = digits.substring(0, lastIdx + len) + numberReplacement[maxIdx] + digits.substring(lastIdx + len)
        }
        digits = digits.replace("[a-zA-Z]+".toRegex(), "")
        digits = digits.first().toString() + digits.last()
        val value = digits.toInt()
        sum += value
    }

    println("Part 2: $sum")
}