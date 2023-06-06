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
                val row = line.split(",").toMutableList()
                data.add(row)
                line = reader.readLine()
            }
        }
        return data
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
