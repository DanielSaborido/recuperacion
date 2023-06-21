import entities.*
import java.io.*
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileReader
import java.io.FileWriter

class CSVFileHandler(private val filePath: String) {
    fun indexCarpeta(): List<File> {
        val archivosCSV = mutableListOf<File>()
        val carpetaPrincipal = File(filePath)

        carpetaPrincipal.walk().forEach {
            if (it.isFile && it.extension == "csv") {
                archivosCSV.add(it)
            }
        }
        return archivosCSV
    }

    fun readCSV(file: File): MutableList<MutableList<String>> {
        val data = mutableListOf<MutableList<String>>()
        BufferedReader(FileReader(file)).use { reader ->
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
            when (val char = line[i]) {
                '"' -> insideQuotes = !insideQuotes
                ',' -> {
                    if (insideQuotes) {
                        builder.append('.')
                    } else {
                        row.add(builder.toString().trim().replace("%", ""))
                        builder.clear()
                    }
                }
                else -> builder.append(char)
            }
        }
        row.add(builder.toString().trim().replace("%", ""))
        return row
    }

    private fun writeCSV(file: File, data: List<List<String>>) {
        BufferedWriter(FileWriter(file)).use { writer ->
            for (row in data) {
                val line = row.joinToString(",")
                writer.write(line)
                writer.newLine()
            }
        }
    }

    private fun modifyCSV(linea:MutableList<String>, campo: Int, text: String): String {
        linea[campo] = text
        return linea[campo]
    }

    fun sobrescribirCSV(evaluacion: MutableList<Evaluacion>){
        val carpeta = indexCarpeta()
        for (file in carpeta.indices) {
            val lectura = readCSV(carpeta[file])
            val anadirnota = lectura[2]
            for (i in 0 until evaluacion[file].alumno.size) {
                modifyCSV(anadirnota, i + 7, evaluacion[file].RA[i].nota.toString())
                var UD = 4
                evaluacion[file].CE.forEach { criterioEvaluacion ->
                    val anadirnotaUD = lectura[UD]
                    if (criterioEvaluacion.inicial == evaluacion[file].alumno[i].inicial) {
                        modifyCSV(anadirnotaUD, i + 7, criterioEvaluacion.nota.toString())
                        UD++
                    }
                }
            }
            writeCSV(carpeta[file], lectura)
        }
        println("Archivos sobrescritos.")
    }
}
