import java.io.File

const val PARAMETRO_BASE_DATOS = "-bd"
const val PARAMETRO_PATH_INPUT = "-pi"
const val PARAMETRO_MODULO = "-mo"
const val PARAMETRO_SALIDA = "-salida"
const val opcionsalida = "fichero"

object ProcesadorArgumentos {
    fun procesar(args: Array<String>): MutableList<String>{
        val dbFile = File("./database.mv.db")

        if (dbFile.exists() && args.size >= 2 && args[0] == PARAMETRO_BASE_DATOS) {
            return args.toMutableList()
        }

        var piPresente = false
        var salidaPresente = false
        var moduloPresente = false
        var nombreModulo = "PRO"
        var nombreSalida = ""
        var nombreCarpeta = ""
        val parametros = mutableListOf<String>()

        for (i in args.indices) {
            when (args[i]) {
                PARAMETRO_PATH_INPUT -> {
                    piPresente = true
                    if (!args[i + 1].startsWith("-")) {
                        nombreCarpeta = args[i + 1]
                    }
                    else{
                        println("No se ha introducido el nombre de la carpeta.")
                    }
                }
                PARAMETRO_MODULO -> {
                    moduloPresente = true
                    if (!args[i+1].startsWith("-")) {
                        nombreModulo = args[i + 1]
                    }
                }
                PARAMETRO_SALIDA -> {
                    salidaPresente = true
                    if (!args[i + 1].startsWith("-")) {
                        nombreSalida = args[i + 1]
                    }
                    else{
                        println("No se ha introducido la opcion de salida.")
                    }
                }
            }
        }

        when (piPresente){
            true -> {
                parametros.add(PARAMETRO_PATH_INPUT)
                parametros.add(nombreCarpeta)
            }
            false -> println("No se ha introducido el argumento -pi")
        }
        when (moduloPresente){
            true -> {
                parametros.add(PARAMETRO_MODULO)
                parametros.add(nombreModulo)
            }
            false -> {
                if (piPresente && salidaPresente && nombreSalida == opcionsalida){
                    parametros.add(PARAMETRO_SALIDA)
                    parametros.add(nombreSalida)
                    return parametros
                }
                else{println("El argumento -mo solo se excluye en la salida fichero")}
            }
        }
        when (salidaPresente){
            true -> {
                parametros.add(PARAMETRO_SALIDA)
                parametros.add(nombreSalida)
            }
            false -> println("No se ha introducido el argumento -salida")
        }


        return if (args.any { it == "" }) {
            mutableListOf()
        } else{
            parametros
        }
    }
}