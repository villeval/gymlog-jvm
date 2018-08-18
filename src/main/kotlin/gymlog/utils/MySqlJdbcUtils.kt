package gymlog.utils

import java.sql.*
import java.util.Properties

object MySQLJDBCUtil {

    fun getConnection(): Connection? {
        var connection: Connection? = null
        val connectionProps = Properties()
        connectionProps["user"] = "testi"
        connectionProps["password"] = "password"

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance()
            connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/gymlog?useJDBCCompliantTimezoneShift=true&serverTimezone=UTC",
                    connectionProps
            )
        } catch (ex: SQLException) {
            // handle any errors
            ex.printStackTrace()
        } catch (ex: Exception) {
            // handle any errors
            ex.printStackTrace()
        }

        return connection
    }
}