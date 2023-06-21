package dao

import Database
import de.m3y.kformat.Table
import de.m3y.kformat.table
import entities.Alumno
import interfaces.Conexion
import interfaces.IDataAccess
import java.sql.SQLException

class AlumnoDAO(private val dataSource: Conexion) : IDataAccess<Alumno> {
    override fun create(entity: Alumno) {
        val conn = dataSource.connection()
        val sql = "INSERT INTO ALUMNO (inicial, alumno, modulo, notamedia) VALUES (?, ?, ?, ?)"
        try {
            val stmt = conn.prepareStatement(sql)
            stmt.setString(1, entity.inicial)
            stmt.setString(2, entity.alumno)
            stmt.setString(3, entity.modulo)
            stmt.setString(4, entity.notamedia.toString())
            stmt.executeUpdate()
            stmt.close()
        } catch (ex: SQLException) {
            ex.printStackTrace()
        } finally {
            conn.close()
        }
    }

    override fun selectById(inicial: String): List<Alumno> {
        val sql = "SELECT * FROM ALUMNO WHERE inicial = ?"
        val alumnos = mutableListOf<Alumno>()

        dataSource.connection().use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, inicial)
                val rs = stmt.executeQuery()
                while (rs.next()) {
                    val alumno = Alumno(
                        inicial = rs.getString("inicial"),
                        alumno = rs.getString("alumno"),
                        modulo = rs.getString("modulo"),
                        notamedia = rs.getFloat("notamedia")
                    )
                    alumnos.add(alumno)
                }
            }
        }
        return alumnos
    }


    override fun getAll(): List<Alumno> {
        val conn = dataSource.connection()
        val sql = "SELECT * FROM ALUMNO"
        val alumnoList = mutableListOf<Alumno>()
        try {
            val stmt = conn.createStatement()
            val rs = stmt.executeQuery(sql)
            while (rs.next()) {
                alumnoList.add(
                    Alumno(
                        inicial = rs.getString("inicial"),
                        alumno = rs.getString("alumno"),
                        modulo = rs.getString("modulo"),
                        notamedia = rs.getFloat("notamedia")
                    )
                )
            }
            rs?.close()
            stmt?.close()
        } catch (ex: SQLException) {
            ex.printStackTrace()
        } finally {
            conn.close()
        }
        return alumnoList

    }

    override fun update(entity: Alumno) {
        val conn = dataSource.connection()
        val sql = "UPDATE ALUMNO SET alumno = ?, modulo = ?, notamedia = ? WHERE inicial = ?"
        try {
            val stmt = conn.prepareStatement(sql)
            stmt.setString(1, entity.notamedia.toString())
            stmt.setString(2, entity.modulo)
            stmt.setString(3, entity.alumno)
            stmt.setString(4, entity.inicial)
            stmt.executeUpdate()
            stmt.close()
        } catch (ex: SQLException) {
            ex.printStackTrace()
        } finally {
            conn.close()
        }
    }

    override fun delete(inicial: String) {
        val conn = dataSource.connection()
        InstrumentoDAO(Database()).delete(inicial)
        ResultadoAprendizajeDAO(Database()).delete(inicial)
        val sql = "DELETE FROM ALUMNO WHERE inicial = ?"
        try {
            val stmt = conn.prepareStatement(sql)
            stmt.setString(1, inicial)
            stmt.executeUpdate()
            stmt.close()
        } catch (ex: SQLException) {
            ex.printStackTrace()
        } finally {
            conn.close()
        }
    }

    override fun deleteALL() {
        val conn = dataSource.connection()
        InstrumentoDAO(Database()).deleteALL()
        ResultadoAprendizajeDAO(Database()).deleteALL()
        CriterioEvaluacionDAO(Database()).deleteALL()
        val sql = "DELETE FROM ALUMNO"
        try {
            val stmt = conn.prepareStatement(sql)
            stmt.executeUpdate()
            stmt.close()
        } catch (ex: SQLException) {
            ex.printStackTrace()
        } finally {
            conn.close()
        }
    }

    fun mostrarInformacionAlumno(alumnos: List<Alumno>) {
        if (alumnos.isNotEmpty()) {
            for (alumno in alumnos) {
                val myDataSource = Database()
                val ronda = table {
                    row (
                        "Inicial: "+alumno.inicial,
                        "Nombre: "+alumno.alumno
                    )
                    row (
                        "Modulo: "+alumno.modulo,
                        "Instrumento Media: "+alumno.notamedia
                    )
                    header()
                    val resultaprend = ResultadoAprendizajeDAO(myDataSource).selectById(alumno.inicial)
                    val criteval = CriterioEvaluacionDAO(myDataSource).selectById(alumno.inicial)
                    val nota = InstrumentoDAO(myDataSource).selectById(alumno.inicial)
                    resultaprend.forEach {resultadoAprendizaje ->
                        row(
                            resultadoAprendizaje.RA,
                            resultadoAprendizaje.porcentaje.toString()+"%",
                            resultadoAprendizaje.nota.toString()
                        )
                        header()
                        criteval.forEach {criterioEvaluacion ->
                            if (criterioEvaluacion.UD.contains(resultadoAprendizaje.RA)) {
                                row(
                                    criterioEvaluacion.UD,
                                    criterioEvaluacion.porcentaje.toString() + "%",
                                    criterioEvaluacion.nota.toString()
                                )
                            }
                        }
                        header()
                        nota.forEach {instrumento ->
                            if (instrumento.UD == resultadoAprendizaje.RA) {
                                row(
                                    instrumento.materia + " " + instrumento.UD,
                                    instrumento.porcentaje.toString() + "%",
                                    instrumento.nota.toString()
                                )
                            }
                        }
                        header()
                    }

                    hints {
                        borderStyle = Table.BorderStyle.SINGLE_LINE // or NONE
                    }
                }.render(StringBuilder())
                println(ronda)
            }
        } else {
            println("El alumno seleccionado no existe en la base de datos o la base de datos esta vacia.")
        }
    }
}
