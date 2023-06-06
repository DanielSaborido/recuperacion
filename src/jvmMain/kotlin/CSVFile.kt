import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileReader
import java.io.FileWriter

class CSVFileHandler(private val filePath: String) {

    fun readCSV(): MutableList<MutableList<String>> {
        val data = mutableListOf<MutableList<String>>()
        BufferedReader(FileReader(filePath)).use { reader ->
            var line = reader.readLine()
            while (line != null) {
                val row = parseCSVLine(line)
                data.add(row)
                line = reader.readLine()
            }
        }
        return data
    }

    private fun parseCSVLine(line: String): MutableList<String> {
        val row = mutableListOf<String>()
        val builder = StringBuilder()
        var insideQuotes = false

        for (i in line.indices) {
            val char = line[i]
            when (char) {
                '"' -> insideQuotes = !insideQuotes
                ',' -> if (insideQuotes) {
                    builder.append(char)
                } else {
                    row.add(builder.toString().trim())
                    builder.clear()
                }
                else -> builder.append(char)
            }
        }

        row.add(builder.toString().trim())
        return row
    }

    fun writeCSV(data: List<List<String>>) {
        BufferedWriter(FileWriter(filePath)).use { writer ->
            for (row in data) {
                val line = row.joinToString(",")
                writer.write(line)
                writer.newLine()
            }
        }
    }

    private fun modifyCSV(modifiedData: List<List<String>>) {
        val originalData = readCSV()
        val mergedData = mutableListOf<List<String>>()
        mergedData.addAll(originalData)
        mergedData.addAll(modifiedData)
        writeCSV(mergedData)
    }

}
