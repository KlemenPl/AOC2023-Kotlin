fun main() {
    val input = get_input(15)

    fun hashString(str: String): Int {
        var hash = 0

        for (c in str) {
            hash += c.code
            hash *= 17
            hash %= 256
        }
        return hash
    }

    val initSequence = input[0].split(",")
    var hashSum = 0
    for (seq in initSequence) {
        hashSum += hashString(seq)
    }
    println("Part 1: $hashSum")

    val boxes = Array<ArrayList<String>>(256){ ArrayList() }
    val strengths = Array<HashMap<String, Int>>(256) { HashMap() }

    for (seq in initSequence) {
        val op = if (seq.contains('-')) '-' else '='
        val label = seq.split(op)[0]
        val strength = seq.substring(label.length + 1)
        val boxIdx = hashString(label)

        when (op) {
            '-' -> {
                boxes[boxIdx].remove(label)
            }
            '=' -> {
                if (label !in boxes[boxIdx]) {
                    boxes[boxIdx].add(label)
                }
                strengths[boxIdx][label] = strength.toInt()
            }
            else -> assert(false)
        }

    }
    var focusingPower = 0
    for (boxIdx in boxes.indices) {
        val box = boxes[boxIdx]
        //if (box.isNotEmpty())
        //    println("$boxIdx: $box")
        for (lensIdx in box.indices) {
            val lens = box[lensIdx]
            focusingPower += (boxIdx + 1) * (lensIdx + 1) * strengths[boxIdx][lens]!!
        }
    }
    println("Part 2: $focusingPower")
}