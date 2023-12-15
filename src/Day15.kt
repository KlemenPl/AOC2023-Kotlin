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

    data class Lens(val label: String, var strength: Int = 0)

    val boxes = Array<ArrayList<Lens>>(256){ ArrayList() }


    for (seq in initSequence) {
        val op = if (seq.contains('-')) '-' else '='
        val label = seq.split(op)[0]
        val strength = seq.substring(label.length + 1)
        val boxIdx = hashString(label)
        val box = boxes[boxIdx]

        when (op) {
            '-' -> {
                box.removeIf { it.label == label }
            }
            '=' -> {
                if (!box.any { it.label == label }) {
                    boxes[boxIdx].add(Lens(label))
                }
                val lens = boxes[boxIdx].find { it.label == label }
                lens!!.strength = strength.toInt()
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
            focusingPower += (boxIdx + 1) * (lensIdx + 1) * lens.strength
        }
    }
    println("Part 2: $focusingPower")
}