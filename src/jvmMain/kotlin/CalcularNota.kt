import entities.Evaluacion

object CalcularNota {
    fun calcularNotas(informacion: MutableList<Evaluacion>) {
        informacion.forEach { evaluacion ->
            evaluacion.alumno.forEach { alummno ->
                evaluacion.instrumentos.forEach { instrumento ->
                    if (alummno.inicial == instrumento.inicial) {
                        instrumento.asignacion.forEach { ud ->
                            evaluacion.CE.forEach { CE ->
                                if (alummno.inicial == CE.inicial && CE.UD.endsWith(ud)) {
                                    CE.nota += instrumento.calcularNota()
                                }
                            }
                        }
                    }
                }
                evaluacion.CE.forEach { CE ->
                    if (alummno.inicial == CE.inicial) {
                        evaluacion.RA.forEach { RA ->
                            if (alummno.inicial == RA.inicial) {
                                RA.nota += CE.calcularNota()
                            }
                        }
                    }
                }
            }
        }
    }
    fun calcularMedias(informacion: Evaluacion) {
        informacion.alumno.forEach { alummno ->
            informacion.RA.forEach { RA ->
                if (alummno.inicial == RA.inicial) {
                    alummno.notamedia += RA.calcularNota()
                }
            }
        }
    }
}