import interfaces.Conexion
import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException



class Database : Conexion {

    private val DB = "./database"
    private val DB_URL = "jdbc:h2:${DB}"
    private val USER = "root"
    private val PASS = "toor"

    private val sql = """
                CREATE TABLE IF NOT EXISTS ALUMNO (
                            inicial VARCHAR(5) NOT NULL,
                            alumno VARCHAR(250) NOT NULL,
                            modulo VARCHAR(250) NOT NULL,
                            notamedia FLOAT,
                            PRIMARY KEY (inicial)
                );
                CREATE TABLE IF NOT EXISTS INSTRUMENTO (
                      inicial VARCHAR(5) NOT NULL,
                     UD VARCHAR(50) NOT NULL,
                      asignacion VARCHAR(250) NOT NULL,
                      materia VARCHAR(1000) NOT NULL,
                      porcentaje FLOAT NOT NULL,
                      nota FLOAT NOT NULL,
                     PRIMARY KEY (inicial, UD, materia),
                      FOREIGN KEY (inicial) REFERENCES ALUMNO(inicial)
                );
                CREATE TABLE IF NOT EXISTS CRITERIOEVALUACION (
                     inicial VARCHAR(5) NOT NULL,
                     UD VARCHAR(50) NOT NULL,
                     porcentaje FLOAT,
                     nota FLOAT,
                     PRIMARY KEY (inicial, UD),
                     FOREIGN KEY (inicial) REFERENCES ALUMNO(inicial)
                );
                CREATE TABLE RESULTADOAPRENDIZAJE (
                      inicial VARCHAR(5) NOT NULL,
                      RA VARCHAR(50) NOT NULL,
                      porcentaje FLOAT,
                      nota FLOAT,
                      PRIMARY KEY (inicial, RA),
                      FOREIGN KEY (inicial) REFERENCES ALUMNO(inicial)
                );
        """.trimIndent()

    init {
        loadTable()
    }

    private fun loadTable() {
        val dbFile = File("${DB}.mv.db")
        if (dbFile.exists()) {
            print("")
        } else {
            val conn = connection()
            try {
                val stmt = conn.prepareStatement(sql)
                stmt?.executeUpdate()
                stmt?.close()
            } catch (ex: SQLException) {
                ex.run { printStackTrace() }
            } finally {
                conn.close()
            }
        }
    }

    override fun connection() : Connection {
        return DriverManager.getConnection( DB_URL, USER, PASS)
    }

}