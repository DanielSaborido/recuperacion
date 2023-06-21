import gui.AppGui

const val opcionsalidaF = "fichero"
const val opcionsalidaBD = "bd"
const val opcionsalidaCon = "consola"
const val opcionsalidaCom = "compose"

object LanzadorAplicacion {
    fun lanzar(args: MutableList<String>){
        if (args[0] == PARAMETRO_BASE_DATOS){
            ManejoBD.accesoBD(args)
        }
        else{
            val csvHandler = CSVFileHandler(args[1])
            val archivos = csvHandler.indexCarpeta()
            val infoArch = mutableListOf<List<MutableList<String>>>()
            archivos.forEach { infoArch.add(ManejarInformacion.procesar(csvHandler.readCSV(it))) }
            val evaluaciones = ManejarInformacion.organizar(infoArch,args[3])

            val clase = ManejarInformacion.unificar(evaluaciones)

            when (args.last()) {
                opcionsalidaF -> {//escritura en fichero
                    csvHandler.sobrescribirCSV(evaluaciones)
                }
                opcionsalidaBD -> {//escritura en base de datos
                    ManejoBD.crearBD(clase)
                }
                opcionsalidaCom -> {//escritura en compose
                    AppGui(clase)
                }
                opcionsalidaCon -> {//escritura en consola
                    Consola.infoConsola(clase)
                }
                else -> {
                    println("La opción proporcionada para la salida no es válida.")
                    return
                }
            }
        }
    }
}