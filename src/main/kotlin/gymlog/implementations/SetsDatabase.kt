package gymlog.implementations

import gymlog.models.InputSet
import gymlog.models.Set
import gymlog.models.Sets
import gymlog.utils.MySQLJDBCUtil
import java.sql.*
import javax.sql.DataSource

object SetsDatabase {

    private const val selectSetsWithUserId = "SELECT * FROM SETS WHERE USERID LIKE ? LIMIT ?, ?"
    private const val insertSet = "INSERT INTO SETS VALUES (?,?,?,?,?,?)"
    private const val deleteSet = "DELETE FROM SETS WHERE ID = ? AND USERID = ?"
    private const val updateSet = "UPDATE SETS SET WEIGHT = ?, EXERCISE = ?, REPS = ?, LASTMODIFIEDDATE = ? WHERE ID = ? AND USERID = ?"

    fun getSets(dataSource: DataSource, userId: String, skip: Int, limit: Int): Sets {
        val params = mapOf(
                1 to userId,
                2 to skip,
                3 to limit
        )

        val results = MySQLJDBCUtil.getResults(dataSource, selectSetsWithUserId, params)
        return Sets(
                total = results.size,
                skip = skip,
                limit = limit,
                sets = results.map { row ->
                    println(row)
                    Set(
                            id = row["ID"] as String,
                            userId = row["USERID"] as String,
                            weight = row["WEIGHT"] as Double,
                            exercise = row["EXERCISE"] as String,
                            reps = row["REPS"] as Int,
                            lastModifiedDate = row["LASTMODIFIEDDATE"] as Timestamp
                    )
                }
        )
    }

    fun addSet(dataSource: DataSource, userId: String, inputSet: InputSet): Boolean {
        var preparedStatement: PreparedStatement? = null
        var conn: Connection? = null

        try {
            conn = MySQLJDBCUtil.getConnection(dataSource)
            preparedStatement = conn.prepareStatement(insertSet)
            preparedStatement.setString(1, System.nanoTime().toString())
            preparedStatement.setString(2, userId)
            preparedStatement.setDouble(3, inputSet.weight)
            preparedStatement.setString(4, inputSet.exercise)
            preparedStatement.setInt(5, inputSet.reps)
            preparedStatement.setTimestamp(6, Timestamp(System.currentTimeMillis()))
            println(preparedStatement)

            val resultSet = preparedStatement.executeUpdate()
            println(resultSet)

        } catch (ex: SQLException) {
            ex.printStackTrace()
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close()
                } catch (sqlEx: SQLException) {
                }
            }

            if (conn != null) {
                try {
                    conn.close()
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }
        return true
    }

    fun deleteSet(dataSource: DataSource, setId: String, userId: String): Boolean {
        var preparedStatement: PreparedStatement? = null
        var conn: Connection? = null

        try {
            conn = MySQLJDBCUtil.getConnection(dataSource)
            preparedStatement = conn.prepareStatement(deleteSet)
            preparedStatement.setString(1, setId)
            preparedStatement.setString(2, userId)
            println(preparedStatement)

            val resultSet = preparedStatement.executeUpdate()
            println(resultSet)

        } catch (ex: SQLException) {
            ex.printStackTrace()
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close()
                } catch (sqlEx: SQLException) {
                }
            }

            if (conn != null) {
                try {
                    conn.close()
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }
        return true
    }

    fun updateSet(dataSource: DataSource, setId: String, userId: String, inputSet: InputSet): Boolean {
        var preparedStatement: PreparedStatement? = null
        var conn: Connection? = null

        try {
            conn = MySQLJDBCUtil.getConnection(dataSource)
            preparedStatement = conn.prepareStatement(updateSet)
            preparedStatement.setDouble(1, inputSet.weight)
            preparedStatement.setString(2, inputSet.exercise)
            preparedStatement.setInt(3, inputSet.reps)
            preparedStatement.setTimestamp(4, Timestamp(System.currentTimeMillis()))
            preparedStatement.setString(5, setId)
            preparedStatement.setString(6, userId)
            println(preparedStatement)

            val resultSet = preparedStatement.executeUpdate()
            println(resultSet)

        } catch (ex: SQLException) {
            ex.printStackTrace()
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close()
                } catch (sqlEx: SQLException) {
                }
            }

            if (conn != null) {
                try {
                    conn.close()
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }
        return true
    }
}