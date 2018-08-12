package gymlog.implementations

import java.sql.*
import java.util.Properties

object MySQLJDBCUtil {

    internal var conn: Connection? = null

    fun getConnection() {
        val connectionProps = Properties()
        connectionProps["user"] = "foo"
        connectionProps["password"] = "bar"

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance()
            conn = DriverManager.getConnection(
                    "jdbc:" + "mysql" + "://" +
                            "127.0.0.1" +
                            ":" + "3306" + "/"+
                            "gymlog" + "?useJDBCCompliantTimezoneShift=true&serverTimezone=UTC",
                    connectionProps
            )
        } catch (ex: SQLException) {
            // handle any errors
            ex.printStackTrace()
        } catch (ex: Exception) {
            // handle any errors
            ex.printStackTrace()
        }
    }

    fun executeMySqlQuery(): String {
        var stmt: Statement? = null
        var resultset: ResultSet? = null
        var ret: String = ""

        try {
            var stmt = conn!!.createStatement()
            var resultset = stmt!!.executeQuery("SELECT * FROM SETS")

            if(stmt.execute("SELECT * FROM SETS")) {
                resultset = stmt.resultSet
            }


            while (resultset!!.next()) {
                println(resultset.getString("ID"))
                println(resultset.getString("USERID"))
                println(resultset.getString("WEIGHT"))
                println(resultset.getString("EXERCISE"))
                println(resultset.getString("REPS"))
                ret = resultset.getString("USERID")
            }
        } catch (ex: SQLException) {
            // handle any errors
            ex.printStackTrace()
        } finally {
            // release resources
            if (resultset != null) {
                try {
                    resultset.close()
                } catch (sqlEx: SQLException) {
                }

                resultset = null
            }

            if (stmt != null) {
                try {
                    stmt.close()
                } catch (sqlEx: SQLException) {
                }

                stmt = null
            }

            if (conn != null) {
                try {
                    conn!!.close()
                } catch (sqlEx: SQLException) {
                }

                conn = null
            }
        }

        return ret
    }


}