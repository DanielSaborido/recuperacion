import java.io.File

object OpArchivos {

    fun indexCarpeta(directory: String?): List<File> {
        val archivosLog = mutableListOf<File>()
        val carpetaPrincipal = File("$directory")

        carpetaPrincipal.walk().forEach {
            if (it.isFile /*&& it.extension == "csv"*/) {
                archivosLog.add(it)
            }
        }

        return archivosLog
    }

    fun indexArchivos (nombre: String): List<File> {

        val folder = File("logs/$nombre")
        val files = mutableListOf<File>()

        if (folder.exists() && folder.isDirectory) {
            folder.listFiles()?.forEach {
                if (it.isFile) {
                    files.add(it)
                }
            }
        }

        return files

    }

    fun calcPromedios(): MutableList<Any> {

        val archivos = indexCarpeta("notas")

        // Expresiones Regulares
        val regexNumeros = """############################### Ha salido la bola ([0-9]*)""".toRegex()
        val regexLinea = """El carton ([0-9]*), tiene linea (.*):""".toRegex()
        val regexBingo = """El carton ([0-9]*), tiene BINGO!!!""".toRegex()

        // Variables y Valores
        var numerosHastaBingo = 0
        var numerosHastaLinea = 0
        var numerosLineas = 0
        val cartonesBingo = mutableListOf<Int>()
        val cartonesLinea = mutableListOf<Int>()
        val listaNumeros = mutableMapOf<Int, Int>()

        archivos.forEach { archivo ->

            var bingoEncontrado = false
            var lineaEncontrada = false

            archivo.forEachLine { linea ->

                val matchLinea = regexLinea.find(linea)
                val matchNumero = regexNumeros.find(linea)
                val matchBingo = regexBingo.find(linea)

                if (!lineaEncontrada) {
                    if (matchLinea != null) {
                        lineaEncontrada = true
                    } else {
                        if (matchNumero != null) {
                            numerosHastaLinea++
                        }
                    }
                }

                if (!bingoEncontrado) {
                    if (matchBingo != null) {
                        bingoEncontrado = true
                        val idCarton = matchBingo.groupValues[1].toInt()
                        cartonesBingo.add(idCarton)
                    } else {
                        if (matchNumero != null) {
                            numerosHastaBingo++
                            val numero = matchNumero.groupValues[1].toInt()
                            listaNumeros[numero] = listaNumeros.getOrDefault(numero, 0) + 1
                        }
                        if (matchLinea != null) {
                            numerosLineas++
                            val idCarton = matchLinea.groupValues[1].toInt()
                            cartonesLinea.add(idCarton)
                        }
                    }
                }

            }

        }

        val numerosRepetidos = listaNumeros.entries
            .sortedByDescending { it.value }
            .map { it.key }
            .take(10)

        val promedioLinea = if (numerosHastaLinea > 0) numerosHastaLinea / archivos.size else 0
        val promedioBingo = if (numerosHastaBingo > 0) numerosHastaBingo / archivos.size else 0
        val promedioLineas = if (numerosLineas > 0) numerosLineas / archivos.size else 0
        val idb = cartonesBingo.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key ?: 0
        val idl = cartonesLinea.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key ?: 0

        return mutableListOf(promedioLinea, promedioBingo, promedioLineas, idb, idl, numerosRepetidos)
    }

    fun guardarResumen(args: StringBuilder) {
        val resumenesDir = File("resumenes")
        resumenesDir.mkdirs()

        val numFiles = resumenesDir.listFiles()?.count { it.isFile } ?: 0
        val fileName = "resumen-$numFiles.log"

        val resumenFile = File(resumenesDir, fileName)
        resumenFile.writeText(args.toString())

        if (resumenFile.exists()) {
            println("$resumenFile se ha creado.")
        } else {
            println("$resumenFile no se ha podido crear.")
        }
    }

}