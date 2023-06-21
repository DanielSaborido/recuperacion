package entities

import interfaces.Calculable

open class Nota(override val nota: Float, override val porcentaje: Float) : Calculable {
    fun calcularNota(): Float {
        return nota * (porcentaje/100)
    }
}
