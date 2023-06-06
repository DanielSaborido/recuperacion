fun main() {
    val csvHandler = CSVFileHandler("ra1.csv")

    // Leer archivo CSV
    val lectura = csvHandler.readCSV()
    println(lectura.toString())

    val row = lectura[0]  // Se obtiene la fila
    row[8] = "EL NUEVO VALOR"  // Modificar el tercer elemento de la fila

    // Se escribe en el archivo CSV
    csvHandler.writeCSV(lectura)

    println(csvHandler.readCSV()[0][8])

    // se lee archivo CSV
    println( csvHandler.readCSV().toString() )
    lectura.forEach { it->
        println(it)
    }
}