fun main() {
    val lines = get_input(6)

    val times = lines[0].split("Time:\\s+".toRegex())[1].split("\\s+".toRegex()).map { it.trim().toInt() }
    val distances = lines[1].split("Distance:\\s+".toRegex())[1].split("\\s+".toRegex()).map { it.trim().toInt() }

    fun simulateGame(holdTime: Long, totalTime: Long): Long {
        val speed = holdTime
        return (totalTime - holdTime) * speed
    }

    var numOfWays = 1
    for ((time, dst) in times.zip(distances)) {
        var winCombinations = 0
        for (i in 1 until time) {
            val dstTraveled = simulateGame(i.toLong(), time.toLong())
            if (dstTraveled > dst) {
                winCombinations++
            }
        }
        numOfWays *= winCombinations

    }
    println("Part 1: $numOfWays")

    val timeStr = lines[0].split("Time:\\s+".toRegex())[1].replace(" ", "")
    val dstStr  = lines[1].split("Distance:\\s+".toRegex())[1].replace(" ", "")
    val time = timeStr.toLong()
    val dst = dstStr.toLong()

    numOfWays = 0
    for (i in 1 until time) {
        val dstTraveled = simulateGame(i, time)
        if (dstTraveled > dst) {
            numOfWays++
        }
    }
    println("Part 2: $numOfWays")
}