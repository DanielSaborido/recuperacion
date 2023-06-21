import dao.AlumnoDAO
import dao.CriterioEvaluacionDAO
import dao.InstrumentoDAO
import dao.ResultadoAprendizajeDAO
import entities.Alumno
import entities.Evaluacion

object ManejoBD {
    fun crearBD(evaluacion: Evaluacion) {
        val myDataSource = Database()
        AlumnoDAO(myDataSource).deleteALL()
        evaluacion.alumno.forEach {alumno->
            AlumnoDAO(myDataSource).create(alumno)
        }
        evaluacion.RA.forEach { resultadoAprendizaje ->
            ResultadoAprendizajeDAO(myDataSource).create(resultadoAprendizaje)
        }
        evaluacion.CE.forEach { criterioEvaluacion ->
            CriterioEvaluacionDAO(myDataSource).create(criterioEvaluacion)
        }
        evaluacion.instrumentos.forEach { instrumento ->
            InstrumentoDAO(myDataSource).create(instrumento)
        }
        println("Informacion a la base de datos añadida correctamente")
    }

    fun accesoBD(args: MutableList<String>) {
        if (args.size > 3) {
            println("Sobran argumentos.")
        }

        val eliminar = "d"
        val mostrar = "q"
        val mostrarAlumno = "i"

        val myDataSource = Database()
        when (args[1]) {
            eliminar -> {
                AlumnoDAO(myDataSource).deleteALL()
                println("Informacion de la base de datos eliminada correctamente")
            }

            mostrar -> {
                println("Mostrando informacion de la base de datos por alumnos")
                val aula = AlumnoDAO(myDataSource).getAll()
                AlumnoDAO(myDataSource).mostrarInformacionAlumno(aula)
            }

            mostrarAlumno -> {
                println("Mostrando informacion de la base de datos del alumno seleccionado:")
                val alumno = AlumnoDAO(myDataSource).selectById(args[2])
                AlumnoDAO(myDataSource).mostrarInformacionAlumno(alumno)
            }
        }
    }
}