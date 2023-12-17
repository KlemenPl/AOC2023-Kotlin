import java.util.PriorityQueue

fun main() {
    val input = get_input(17).map { it.toCharArray() }

    data class Node(val x: Int, val y: Int, var cost: Int, val lastDir: Int): Comparable<Node> {
        override fun compareTo(other: Node): Int {
            return cost.compareTo(other.cost)
        }
    }

    fun calculateHeatLoss(minDst: Int, maxDst: Int): Int {
        val DIRS = arrayOf(Pair(-1, 0), Pair(1, 0), Pair(0, -1), Pair(0, 1))

        val pq = PriorityQueue<Node>()
        pq.add(Node(0, 0, 0, -1))
        // (x, y, dir)
        val visited = HashSet<Triple<Int, Int, Int>>()
        val costs = HashMap<Triple<Int, Int, Int>, Int>()

        val sizeX = input[0].size
        val sizeY = input.size
        val finishX = sizeX - 1
        val finishY = sizeY - 1

        while (pq.isNotEmpty()) {
            val node = pq.poll()
            val nodeKey = Triple(node.x, node.y, node.lastDir)
            if (nodeKey in visited)
                continue
            visited.add(nodeKey)

            if (node.x == finishX && node.y == finishY) {
                return node.cost
            }
            for (dirIdx in DIRS.indices) {
                // Cant repeat same direction
                if (dirIdx == node.lastDir)
                    continue
                val dir = DIRS[dirIdx]
                if (node.lastDir != -1) {
                    val prevDir = DIRS[node.lastDir]
                    // Cant move back
                    if (dir.first == -prevDir.first && dir.second == 0 ||
                        dir.second == -prevDir.second && dir.first == 0)
                        continue
                }
                for (dst in minDst..maxDst) {
                    val adjX = node.x + dir.first * dst
                    val adjY = node.y + dir.second * dst
                    if (adjX !in 0..<sizeX || adjY !in 0..<sizeY)
                        continue
                    var cost = node.cost
                    for (d in 1..dst) {
                        val x = node.x + dir.first * d
                        val y = node.y + dir.second * d
                        cost += input[y][x].digitToInt()
                    }
                    val adjNode = Node(adjX, adjY, cost, dirIdx)
                    val key = Triple(adjX, adjY, dirIdx)
                    if (key in costs && costs[key]!! < cost)
                        continue
                    pq.add(adjNode)
                    costs[key] = cost
                }
            }
        }
        return -1
    }

    println("Part 1: ${calculateHeatLoss(1, 3)}")
    println("Part 2: ${calculateHeatLoss(4, 10)}")


}