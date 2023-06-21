package dao

import entities.ResultadoAprendizaje
import interfaces.Conexion
import interfaces.IDataAccess
import java.sql.SQLException

class ResultadoAprendizajeDAO(private val dataSource: Conexion) : IDataAccess<ResultadoAprendizaje> {
    override fun create(entity: ResultadoAprendizaje) {
        val conn = dataSource.connection()
        val sql = "INSERT INTO RESULTADOAPRENDIZAJE (inicial, RA, porcentaje, nota) VALUES (?, ?, ?, ?)"
        try {
            val stmt = conn.prepareStatement(sql)
                stmt.setString(1, entity.inicial)
                stmt.setString(2, entity.RA)
                stmt.setString(3, entity.porcentaje.toString())
                stmt.setString(4, entity.nota.toString())
                stmt.executeUpdate()
                stmt.close()
            } catch (ex: SQLException) {
            ex.printStackTrace()
        } finally {
            conn.close()
        }
    }

    override fun selectById(inicial: String): List<ResultadoAprendizaje> {
        val sql = "SELECT * FROM RESULTADOAPRENDIZAJE WHERE inicial = ?"
        val resultados = mutableListOf<ResultadoAprendizaje>()

        dataSource.connection().use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, inicial)
                val rs = stmt.executeQuery()
                while (rs.next()) {
                    val resultado = ResultadoAprendizaje(
                        inicial = rs.getString("inicial"),
                        RA = rs.getString("RA"),
                        porcentaje = rs.getFloat("porcentaje"),
                        nota = rs.getFloat("nota")
                    )
                    resultados.add(resultado)
                }
            }
        }

        return resultados
    }

    override fun getAll(): List<ResultadoAprendizaje> {
        val conn = dataSource.connection()
        val sql = "SELECT * FROM RESULTADOAPRENDIZAJE"
        val raList = mutableListOf<ResultadoAprendizaje>()
        try {
            val stmt = conn.createStatement()
            val rs = stmt.executeQuery(sql)
            while (rs.next()) {
                raList.add(
                        ResultadoAprendizaje(
                            inicial = rs.getString("inicial"),
                            RA = rs.getString("RA"),
                            porcentaje = rs.getFloat("porcentaje"),
                            nota = rs.getFloat("nota")
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
        return raList

    }

    override fun update(entity: ResultadoAprendizaje) {
        val conn = dataSource.connection()
        val sql = "UPDATE RESULTADOAPRENDIZAJE SET nota = ?,porcentaje = ?, inicial = ? WHERE RA = ?"
        try {
            val stmt = conn.prepareStatement(sql)
                stmt.setString(1, entity.nota.toString())
                stmt.setString(2, entity.porcentaje.toString())
                stmt.setString(3, entity.inicial)
                stmt.setString(4, entity.RA)
                stmt.executeUpdate()
            stmt.close()
        } catch (ex: SQLException) {
            ex.printStackTrace()
        } finally {
            conn.close()
        }
    }

    override fun delete(RA: String) {
        val sql = "DELETE FROM RESULTADOAPRENDIZAJE WHERE RA = ?"
        dataSource.connection().use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, RA)
                stmt.executeUpdate()
            }
        }
    }

    override fun deleteALL() {
        val conn = dataSource.connection()
        val sql = "DELETE FROM RESULTADOAPRENDIZAJE"
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
}
