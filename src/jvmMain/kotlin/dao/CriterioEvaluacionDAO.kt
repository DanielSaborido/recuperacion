package dao

import entities.CriterioEvaluacion
import interfaces.Conexion
import interfaces.IDataAccess
import java.sql.SQLException

class CriterioEvaluacionDAO(private val dataSource: Conexion) : IDataAccess<CriterioEvaluacion> {
    override fun create(entity: CriterioEvaluacion) {
        val conn = dataSource.connection()
        val sql = "INSERT INTO CRITERIOEVALUACION (inicial, UD, porcentaje, nota) VALUES (?, ?, ?, ?)"
        try {
            val stmt = conn.prepareStatement(sql)
                stmt.setString(1, entity.inicial)
                stmt.setString(2, entity.UD)
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

    override fun selectById(inicial: String): List<CriterioEvaluacion> {
        val sql = "SELECT * FROM CRITERIOEVALUACION WHERE inicial = ?"
        val resultados = mutableListOf<CriterioEvaluacion>()

        dataSource.connection().use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, inicial)
                val rs = stmt.executeQuery()
                while (rs.next()) {
                    val resultado = CriterioEvaluacion(
                        inicial = rs.getString("inicial"),
                        UD = rs.getString("UD"),
                        porcentaje = rs.getFloat("porcentaje"),
                        nota = rs.getFloat("nota")
                    )
                    resultados.add(resultado)
                }
            }
        }

        return resultados
    }

    override fun getAll(): List<CriterioEvaluacion> {
        val conn = dataSource.connection()
        val sql = "SELECT * FROM CRITERIOEVALUACION"
        val raList = mutableListOf<CriterioEvaluacion>()
        try {
            val stmt = conn.createStatement()
            val rs = stmt.executeQuery(sql)
            while (rs.next()) {
                raList.add(
                    CriterioEvaluacion(
                            inicial = rs.getString("inicial"),
                            UD = rs.getString("UD"),
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

    override fun update(entity: CriterioEvaluacion) {
        val conn = dataSource.connection()
        val sql = "UPDATE CRITERIOEVALUACION SET nota = ?,porcentaje = ?, inicial = ? WHERE UD = ?"
        try {
            val stmt = conn.prepareStatement(sql)
                stmt.setString(1, entity.nota.toString())
                stmt.setString(2, entity.porcentaje.toString())
                stmt.setString(3, entity.inicial)
                stmt.setString(4, entity.UD)
                stmt.executeUpdate()
            stmt.close()
        } catch (ex: SQLException) {
            ex.printStackTrace()
        } finally {
            conn.close()
        }
    }

    override fun delete(inicial: String) {
        val sql = "DELETE FROM CRITERIOEVALUACION WHERE inicial = ?"
        dataSource.connection().use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, inicial)
                stmt.executeUpdate()
            }
        }
    }

    override fun deleteALL() {
        val conn = dataSource.connection()
        val sql = "DELETE FROM CRITERIOEVALUACION"
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
