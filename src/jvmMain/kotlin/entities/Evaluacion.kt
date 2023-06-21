package entities

data class Evaluacion(
    val alumno: List<Alumno>,
    val RA: List<ResultadoAprendizaje>,
    val CE: List<CriterioEvaluacion>,
    val instrumentos: List<Instrumento>
)

