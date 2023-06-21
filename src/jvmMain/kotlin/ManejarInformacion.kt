import entities.*

object ManejarInformacion {
    fun procesar(informacion: MutableList<MutableList<String>>): List<MutableList<String>> {
        val listaFiltrada = mutableListOf<MutableList<String>>()
        informacion.forEach { sublista ->
            val sublistaFiltrada = sublista.filter { it.isNotEmpty() }.toMutableList()
            listaFiltrada.add(sublistaFiltrada)
        }
        return listaFiltrada.filter { it.isNotEmpty() && it.size>2 && !(it.contains("NPR") || it.contains("AP"))}
    }

    fun organizar(informacion: MutableList<List<MutableList<String>>>, modulo: String): MutableList<Evaluacion>{
        val curso = mutableListOf<Evaluacion>()
        informacion.forEach { evaluacion ->
            val alumnos = mutableListOf<Alumno>()
            val resutaprend = mutableListOf<ResultadoAprendizaje>()
            val criteval = mutableListOf<CriterioEvaluacion>()
            val instrumentos = mutableListOf<Instrumento>()
            for (i in 0 until evaluacion[0].size){
                alumnos.add(Alumno(evaluacion[0][i],evaluacion[1][i+2],modulo))
                resutaprend.add(ResultadoAprendizaje(evaluacion[0][i],evaluacion[2][0],evaluacion[2][3].toFloat(),0.0F))
                evaluacion.forEach {
                    if (it[0].startsWith("${evaluacion[2][0]}.")) {
                        criteval.add(CriterioEvaluacion(evaluacion[0][i], it[0], it[3].toFloat(), 0.0F))
                    }
                    if (it[0][0].isLowerCase()) {
                        instrumentos.add(Instrumento(evaluacion[0][i], evaluacion[2][0], it[0], it[1], it[2].toFloat(), it[i + 3].toFloat()))
                    }
                }
            }
            curso.add(Evaluacion(alumnos,resutaprend,criteval,instrumentos))
        }
        CalcularNota.calcularNotas(curso)
        return curso
    }

    fun unificar(evaluaciones: MutableList<Evaluacion>): Evaluacion{
        val alumnos = mutableListOf<Alumno>()
        val resutaprend = mutableListOf<ResultadoAprendizaje>()
        val criteval = mutableListOf<CriterioEvaluacion>()
        val instrumentos = mutableListOf<Instrumento>()
        evaluaciones[0].alumno.forEach { alumnos.add(Alumno(it.inicial,it.alumno,it.modulo,it.notamedia)) }
        evaluaciones.forEach { evaluacion ->
            evaluacion.RA.forEach { resutaprend.add(ResultadoAprendizaje(it.inicial,it.RA,it.porcentaje,it.nota)) }
            evaluacion.CE.forEach { criteval.add(CriterioEvaluacion(it.inicial,it.UD,it.porcentaje,it.nota)) }
            evaluacion.instrumentos.forEach { instrumentos.add(Instrumento(it.inicial, it.UD, it.asignacion, it.materia, it.porcentaje, it.nota)) }
        }
        CalcularNota.calcularMedias(Evaluacion(alumnos,resutaprend,criteval,instrumentos))
        return Evaluacion(alumnos,resutaprend,criteval,instrumentos)
    }
}