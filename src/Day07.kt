fun main() {
    val lines = get_input(7)

    data class Card(val hand: String, val bid: Int)

    val cards = lines.map {
        val line = it.split(" ")
        Card(line[0], line[1].toInt())
    }

    val CARDS = "AKQJT98765432".reversed()
    val JOKER_CARDS = "AKQT98765432J".reversed()

    class Comparator(val joker: Boolean) {

        fun cardScore(card: Card): Int {
            val numJokers = card.hand.count{ it == 'J' }
            val dupes = card.hand.groupingBy { it }.eachCount().toMutableMap()
            if (joker && numJokers > 0) {
                dupes['J'] = 0
                val max = dupes.maxBy { it.value }
                dupes[max.key] = dupes[max.key]!! + numJokers
            }
            // Five of kind
            if (dupes.values.contains(5)) return 7
            // Four of kind
            if (dupes.values.contains(4)) return 6
            // Full house
            if (dupes.values.contains(3) && dupes.values.contains(2)) return 5
            // Three of kind
            if (dupes.values.contains(3)) return 4
            // Two pair
            if (dupes.values.count { it == 2 } == 2) return 3
            // One pair
            if (dupes.values.contains(2)) return 2
            // High card
            return 1
        }

        fun cmp(lhs: Card, rhs: Card): Int {
            val lScore = cardScore(lhs)
            val rScore = cardScore(rhs)
            if (lScore != rScore) return (rScore - lScore).coerceIn(-1..1)

            for ((l, r) in lhs.hand.zip(rhs.hand)) {
                val cards = if (joker) JOKER_CARDS else CARDS
                val c = (cards.indexOf(r) - cards.indexOf(l)).coerceIn(-1..1)
                if (c != 0) return c
            }
            return 0
        }
    }

    val comparator = Comparator(false)
    val sortedCards = cards.sortedBy { comparator.cardScore(it) }.sortedWith(comparator::cmp).reversed()
    var totalWinnings = 0
    for (i in sortedCards.indices) {
        val card = sortedCards[i]
        totalWinnings += (i + 1) * card.bid
    }
    println("Part 1: $totalWinnings")

    val comparatorJoker = Comparator(true)
    val sortedJokersCards = cards.sortedBy { comparatorJoker.cardScore(it) }.sortedWith(comparatorJoker::cmp).reversed()
    totalWinnings = 0
    for (i in sortedJokersCards.indices) {
        val card = sortedJokersCards[i]
        totalWinnings += (i + 1) * card.bid
    }
    println("Part 2: $totalWinnings")


}