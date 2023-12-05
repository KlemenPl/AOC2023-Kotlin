import kotlin.math.min

fun main() {
    val lines = get_input(5)

    val seeds = lines[0].split(": ")[1].split(" ").map { it.toLong() }

    data class SeedRange(var from: Long, var to: Long)
    data class MapRange(val from: Long, val to: Long, val dstStart: Long) {
        fun canMap(seed: Long): Boolean {
            return seed in from..to
        }
        fun map(seed: Long): Long {
            assert(seed in from..to)
            val dif = seed - from;
            return dstStart + dif
        }
        fun map(range: SeedRange): SeedRange {
            return SeedRange(map(range.from), map(range.to))
        }
        fun mapRange(inputRanges: ArrayList<SeedRange>, unmapped: ArrayList<SeedRange>, mapped: ArrayList<SeedRange>) {
            for (range in inputRanges) {
                if (range.to < from || range.from > to) {
                    // Unmappable
                    unmapped.add(range)
                    continue
                }
                if (range.from < from && range.to in from..to) {
                    // Left side overlaps
                    unmapped.add(SeedRange(range.from, from - 1))
                    mapped.add(map(SeedRange(from, range.to)))
                } else if (range.to > to && range.from in from..to) {
                    // Right side overlaps
                    unmapped.add(SeedRange(to + 1, range.to))
                    mapped.add(map(SeedRange(range.from, to)))
                } else if (range.from >= from && range.to <= to) {
                    // Fully contained
                    mapped.add(map(range))
                } else if (from >= range.from && to <= range.to) {
                    // Range fully contained
                    mapped.add(map(SeedRange(from, to)))
                    unmapped.add(SeedRange(range.from, from - 1))
                    unmapped.add(SeedRange(to + 1, range.to))
                } else {
                    assert(false)
                }
            }
        }

    }

    val seedMap = ArrayList<ArrayList<MapRange>>()

    var curMap = ArrayList<MapRange>()
    for (lineIdx in 1 until lines.size) {
        val line = lines[lineIdx]
        if (line.isBlank()) {
            if (curMap.isNotEmpty()) {
                seedMap.add(curMap)
                curMap = ArrayList()
            }
            continue
        }
        if (line.contains("map:")) continue
        val mapValues = line.split(" ").map { it.toLong() }
        val dst = mapValues[0]
        val src = mapValues[1]
        val srcEnd = mapValues[2] + src
        curMap.add(MapRange(src, srcEnd, dst))
    }
    seedMap.add(curMap)

    fun mapSeed(seed: Long, map: ArrayList<MapRange>): Long {
        var minDst = seed
        for (range in map) {
            if (range.canMap(seed)) {
                minDst = range.map(seed)
                break
            }
        }
        return minDst
    }

    var minLocation = Long.MAX_VALUE
    for (seed in seeds) {
        var seedLocation = seed
        for (map in seedMap) {
            seedLocation = mapSeed(seedLocation, map)
        }
        minLocation = min(minLocation, seedLocation)
    }

    println("Part 1: $minLocation")

    fun mapSeedRanges(inputRanges: ArrayList<SeedRange>, outputRanges: ArrayList<SeedRange>, map: ArrayList<MapRange>) {
        for (range in map) {
            val unmappedRanges = ArrayList<SeedRange>()
            range.mapRange(inputRanges, unmappedRanges, outputRanges)
            inputRanges.clear()
            if (unmappedRanges.isEmpty()) break
            inputRanges.addAll(unmappedRanges)
        }
        // All unmapped map to same range
        outputRanges.addAll(inputRanges)
    }

    minLocation = Long.MAX_VALUE
    val inputSeedRanges = seeds.chunked(2).map { SeedRange(it[0], it[0] + it[1] - 1) }
    for (inputRange in inputSeedRanges) {
        val seedRanges = ArrayList<SeedRange>()
        seedRanges.add(inputRange)

        for (map in seedMap) {
            val outRanges = ArrayList<SeedRange>()
            mapSeedRanges(seedRanges, outRanges, map)
            seedRanges.clear()
            seedRanges.addAll(outRanges)
        }
        val curMin = seedRanges.minOf { it.from }
        minLocation = min(minLocation, curMin)
    }
    println("Part 2: $minLocation")
}