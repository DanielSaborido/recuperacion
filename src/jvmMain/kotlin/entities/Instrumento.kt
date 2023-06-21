package entities

data class Instrumento(val inicial: String, val UD: String, val asignacion: String, var materia: String, override var porcentaje: Float, override var nota: Float): Nota(nota, porcentaje)