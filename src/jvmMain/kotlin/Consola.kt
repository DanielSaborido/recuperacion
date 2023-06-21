import de.m3y.kformat.Table
import de.m3y.kformat.table
import entities.Evaluacion
import java.io.File

object Consola{
    fun infoConsola(clase: Evaluacion) {
        val archivo = StringBuilder()
        clase.alumno.forEach { alumno ->
            val ronda = table {
                row(
                    alumno.inicial,
                    alumno.alumno
                )
                row(
                    alumno.modulo,
                    alumno.notamedia
                )
                header()
                clase.RA.forEach { resultadoAprendizaje ->
                    if (resultadoAprendizaje.inicial == alumno.inicial) {
                        row(
                            resultadoAprendizaje.RA,
                            resultadoAprendizaje.porcentaje.toString() + "%",
                            resultadoAprendizaje.nota.toString()
                        )
                        header()
                        clase.CE.forEach { criterioEvaluacion ->
                            if (criterioEvaluacion.inicial == alumno.inicial && criterioEvaluacion.UD.contains(resultadoAprendizaje.RA)) {
                                row(
                                    criterioEvaluacion.UD,
                                    criterioEvaluacion.porcentaje.toString() + "%",
                                    criterioEvaluacion.nota.toString()
                                )
                            }
                        }
                        header()
                        clase.instrumentos.forEach { instrumento ->
                            if (instrumento.inicial == alumno.inicial && instrumento.UD == resultadoAprendizaje.RA) {
                                row(
                                    instrumento.materia + " " + instrumento.UD,
                                    instrumento.porcentaje.toString() + "%",
                                    instrumento.nota.toString()
                                )
                            }
                        }
                        header()
                    }
                }

                hints {
                    borderStyle = Table.BorderStyle.SINGLE_LINE // or NONE
                }
            }.render(StringBuilder())
            println(ronda)
            archivo.append(ronda.toString() + "\n\n")
        }
        guardarResumen(archivo)
    }

    private fun guardarResumen(args: StringBuilder) {
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
