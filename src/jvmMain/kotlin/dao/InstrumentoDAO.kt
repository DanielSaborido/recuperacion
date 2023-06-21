package dao


import interfaces.Conexion
import entities.Instrumento
import interfaces.IDataAccess
import java.sql.SQLException

class InstrumentoDAO(private val dataSource: Conexion) : IDataAccess<Instrumento> {
    override fun create(entity: Instrumento) {
        val conn = dataSource.connection()
        val sql = "INSERT INTO INSTRUMENTO (inicial, UD, asignacion, materia, porcentaje, nota) VALUES (?, ?, ?, ?, ?, ?)"
        try {
            val stmt = conn.prepareStatement(sql)
                stmt.setString(1, entity.inicial)
                stmt.setString(2, entity.UD)
                stmt.setString(3, entity.asignacion)
                stmt.setString(4, entity.materia)
                stmt.setString(5, entity.porcentaje.toString())
                stmt.setString(6, entity.nota.toString())
                stmt.executeUpdate()
                stmt.close()
            } catch (ex: SQLException) {
            ex.printStackTrace()
        } finally {
            conn.close()
        }
    }

    override fun selectById(inicial: String): List<Instrumento> {
        val sql = "SELECT * FROM INSTRUMENTO WHERE inicial = ?"
        val instrumentos = mutableListOf<Instrumento>()

        dataSource.connection().use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, inicial)
                val rs = stmt.executeQuery()
                while (rs.next()) {
                    val instrumento = Instrumento(
                        inicial = rs.getString("inicial"),
                        UD = rs.getString("UD"),
                        asignacion = rs.getString("asignacion"),
                        materia = rs.getString("materia"),
                        porcentaje = rs.getFloat("porcentaje"),
                        nota = rs.getFloat("nota")
                    )
                    instrumentos.add(instrumento)
                }
            }
        }

        return instrumentos
    }


    override fun getAll(): List<Instrumento> {
        val conn = dataSource.connection()
        val sql = "SELECT * FROM INSTRUMENTO"
        val instrumentoList = mutableListOf<Instrumento>()
        try {
            val stmt = conn.createStatement()
            val rs = stmt.executeQuery(sql)
            while (rs.next()) {
                instrumentoList.add(
                        Instrumento(
                            inicial = rs.getString("inicial"),
                            UD = rs.getString("UD"),
                            asignacion = rs.getString("asignacion"),
                            materia = rs.getString("materia"),
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
        return instrumentoList

    }

    override fun update(entity: Instrumento) {
        val conn = dataSource.connection()
        val sql = "UPDATE INSTRUMENTO SET materia = ?, asignacion = ?, porcentaje = ?, nota = ? WHERE inicial = ? AND UD = ?"
        try {
            val stmt = conn.prepareStatement(sql)
                stmt.setString(6, entity.nota.toString())
                stmt.setString(5, entity.porcentaje.toString())
                stmt.setString(4, entity.asignacion)
                stmt.setString(3, entity.materia)
                stmt.setString(2, entity.UD)
                stmt.setString(1, entity.inicial)
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
        val sql = "DELETE FROM INSTRUMENTO WHERE inicial = ?"
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
        val sql = "DELETE FROM INSTRUMENTO"
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
