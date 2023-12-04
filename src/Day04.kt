fun main() {
    val lines = get_input(4)
    println(lines)

    fun getWinningNumbers(line: String): List<Int> {
        val winNumbers = ArrayList<Int>()
        val winningSet = HashSet<Int>()
        var winningNumbers = line.split(" | ")[0]
        winningNumbers = winningNumbers.split(": ")[1]
        for (number in winningNumbers.split("\\s+".toRegex())) {
            if (number.isBlank()) continue
            winningSet.add(number.toInt())
        }
        val cardNumbers = line.split(" | ")[1]
        for (number in cardNumbers.split("\\s+".toRegex())) {
            if (number.isBlank()) continue
            if (winningSet.contains(number.toInt())) {
                winNumbers.add(number.toInt())
            }
        }
        return winNumbers
    }



    var points = 0
    for (line in lines) {
        val winNumbers = getWinningNumbers(line)
        var cardPoints = 0
        for (number in winNumbers) {
            if (cardPoints == 0)
                cardPoints++
            else
                cardPoints *= 2
        }
        points += cardPoints
    }

    println("Part 1 $points")

    val cardCount = HashMap<Int, Int>()
    val cardQueue = ArrayDeque<Int>()
    cardQueue.addAll(1..lines.size)


    while (cardQueue.isNotEmpty()) {
        val game = cardQueue.removeFirst()
        cardCount[game] = cardCount.getOrDefault(game, 0)  + 1

        val line = lines[game - 1]
        val winNumbers = getWinningNumbers(line)
        var gameIdx = game
        for (number in winNumbers) {
            gameIdx++
            cardQueue.addLast(gameIdx)
        }

    }

    val numCards = cardCount.values.sum()
    println("Part 2 $numCards")

}
