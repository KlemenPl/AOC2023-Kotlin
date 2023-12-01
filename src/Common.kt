import java.io.File
import java.net.HttpURLConnection
import java.net.URL

fun get_input(day_num: Int): ArrayList<String> {
    val lines = ArrayList<String>();
    val file = File("inputs/input_$day_num.txt")
    if (!file.exists()) {
        val url = "https://adventofcode.com/2023/day/$day_num/input"
        val urlCon = URL(url).openConnection() as HttpURLConnection
        urlCon.setRequestProperty(
                "Cookie",
                "session=${File("session.txt").readText()}"
        )
        urlCon.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:120.0) Gecko/20100101 Firefox/120.0")

        try {
            val inputLines = urlCon.inputStream.bufferedReader().readLines()
            File("inputs/").mkdirs()
            file.createNewFile()
            file.printWriter().use { out ->
                inputLines.forEach {
                    out.println(it)
                }
            }
        } finally {
            urlCon.disconnect()
        }
    }
    lines.addAll(file.readLines())
    return lines;
}