package entities

import interfaces.Calculable

data class ResultadoAprendizaje(val inicial: String, val RA: String, override val porcentaje: Float, override var nota: Float): Nota(nota, porcentaje)