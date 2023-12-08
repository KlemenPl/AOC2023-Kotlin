fun main() {
    val lines = get_input(8)

    val instruction = lines[0]
    data class Node(val left: String, val right: String)
    val map = HashMap<String, Node>()

    for (i in 2 until lines.size) {
        val line = lines[i].replace("(", "").replace(")", "").split(" = ")
        val node = line[0]
        val left = line[1].split(", ")[0]
        val right = line[1].split(", ")[1]
        map[node] = Node(left, right)
    }

    fun followInstruction(startNode: String, endCondition: (node: String) -> Boolean): Long {
        var curNode = startNode
        var stepCount = 0L
        var insIdx = 0
        while (!endCondition(curNode)) {
            val dir = instruction[insIdx]
            if (dir == 'R') {
                curNode = map[curNode]!!.right
            } else if (dir == 'L') {
                curNode = map[curNode]!!.left
            }
            stepCount++
            insIdx = (insIdx + 1) % instruction.length
        }
        return stepCount
    }
    val stepCount = followInstruction("AAA") { node -> node == "ZZZ" }
    println("Part 1: $stepCount")


    fun gcd(numA: Long, numB: Long): Long {
        var a = numA
        var b = numB
        while (b > 0) {
            val t = b
            b = a % b
            a = t
        }
        return a
    }
    fun lcm(a: Long, b: Long): Long {
        return a * (b / gcd(a, b))
    }

    val startNodes = map.keys.filter { it.endsWith("A") }
    val stepsRequired = startNodes.map { followInstruction(it) { it.endsWith("Z") } }
    println("Part 2: ${stepsRequired.reduce { acc, i -> lcm(acc, i)}}")



}