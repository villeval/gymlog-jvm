package gymlog.implementations

import gymlog.models.InputSet
import gymlog.models.Set
import gymlog.models.Sets
import java.sql.*

object SetsDatabase {

    private const val selectSetsWithUserId = "SELECT * FROM SETS WHERE USERID LIKE ? LIMIT ?, ?"
    private const val insertSet = "INSERT INTO SETS VALUES (?,?,?,?,?,?)"
    private const val deleteSet = "DELETE FROM SETS WHERE ID = ? AND USERID = ?"
    private const val updateSet = "UPDATE SETS SET WEIGHT = ?, EXERCISE = ?, REPS = ?, LASTMODIFIEDDATE = ? WHERE ID = ? AND USERID = ?"

    fun getSets(userId: String, skip: Int, limit: Int): Sets {
        var preparedStatement: PreparedStatement? = null
        val listOfSets = mutableListOf<Set>()
        var conn: Connection? = null

        try {
            conn = MySQLJDBCUtil.getConnection()
            preparedStatement = conn!!.prepareStatement(selectSetsWithUserId)
            preparedStatement!!.setString(1, userId)
            preparedStatement.setInt(2, skip)
            preparedStatement.setInt(3, limit)
            println(preparedStatement)

            val resultSet = preparedStatement.executeQuery()

            while (resultSet.next()) {
                listOfSets.add(Set(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getDouble(3),
                        resultSet.getString(4),
                        resultSet.getInt(5),
                        resultSet.getTimestamp(6)
                ))
            }
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
        return Sets(
                total = listOfSets.size,
                skip = skip,
                limit = limit,
                sets = listOfSets
        )
    }

    fun addSet(userId: String, inputSet: InputSet): Boolean {
        var preparedStatement: PreparedStatement? = null
        var conn: Connection? = null

        try {
            conn = MySQLJDBCUtil.getConnection()
            preparedStatement = conn!!.prepareStatement(insertSet)
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

    fun deleteSet(setId: String, userId: String): Boolean {
        var preparedStatement: PreparedStatement? = null
        var conn: Connection? = null

        try {
            conn = MySQLJDBCUtil.getConnection()
            preparedStatement = conn!!.prepareStatement(deleteSet)
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

    fun updateSet(setId: String, userId: String, inputSet: InputSet): Boolean {
        var preparedStatement: PreparedStatement? = null
        var conn: Connection? = null

        try {
            conn = MySQLJDBCUtil.getConnection()
            preparedStatement = conn!!.prepareStatement(updateSet)
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