import kotlin.math.max

fun main() {
    val lines = get_input(2)
    println(lines)

    val bag = HashMap<String, Int>()
    var sumIDs = 0

    for (line in lines) {
        val game =  line.split(":\\s+".toRegex())
        val gameID = game[0].split(" ")[1].trim().toInt()
        val sets = game[1].split(";\\s+".toRegex())
        var validGame = true
        for (set in sets) {
            val grabbed = set.split(",\\s+".toRegex())
            bag.clear()
            bag["blue"] = 14
            bag["red"] = 12
            bag["green"] = 13
            for (grab in grabbed) {
                val amount = grab.split(" ")[0].toInt()
                val colour = grab.split(" ")[1].trim()

                bag[colour] = bag[colour]!! - amount
                if (bag[colour]!! < 0) {
                    validGame = false;
                    break
                }
            }
            if (!validGame) break
        }
        if (validGame) {
            sumIDs += gameID
            println(gameID)
        }
    }

    println("Part 1: $sumIDs")

    var sumPowers = 0
    for (line in lines) {
        val game =  line.split(":\\s+".toRegex())
        val sets = game[1].split(";\\s+".toRegex())
        bag.clear()
        bag["blue"] = 0
        bag["red"] = 0
        bag["green"] = 0
        for (set in sets) {
            val grabbed = set.split(",\\s+".toRegex())
            for (grab in grabbed) {
                val amount = grab.split(" ")[0].toInt()
                val colour = grab.split(" ")[1].trim()

                bag[colour] = max(bag[colour]!!, amount)
            }
        }
        sumPowers += bag.values.reduce {
            a, b -> a * b
        }

    }

    println("Part 2: $sumPowers")
}
