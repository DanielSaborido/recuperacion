package entities

import interfaces.Calculable

data class CriterioEvaluacion(val inicial: String, val UD: String, override val porcentaje: Float, override var nota: Float): Nota(nota, porcentaje)