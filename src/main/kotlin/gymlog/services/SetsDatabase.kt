package gymlog.services

import gymlog.models.InputSet
import gymlog.models.Set
import gymlog.models.Sets
import gymlog.utils.MySQLJDBCUtil
import java.sql.*
import java.time.LocalDate
import java.time.LocalDateTime
import javax.sql.DataSource

object SetsDatabase {

    private const val getSetsQuery = "SELECT * FROM SETS WHERE USERID LIKE ? LIMIT ?, ?"
    private const val insertSetQuery = "INSERT INTO SETS VALUES (?,?,?,?,?,?,?)"
    private const val deleteSetQuery = "DELETE FROM SETS WHERE ID = ? AND USERID = ?"
    private const val updateSetQuery = "UPDATE SETS SET EXERCISE = ?, WEIGHT = ?, REPS = ?, LASTMODIFIEDDATE = ? WHERE ID = ? AND USERID = ?"

    fun getSets(dataSource: DataSource, userId: String, skip: Int, limit: Int): Sets {
        val params = mapOf(
                1 to userId,
                2 to skip,
                3 to limit
        )

        val results = MySQLJDBCUtil.doQuery(dataSource, getSetsQuery, params)
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
                            lastModifiedDate = row["LASTMODIFIEDDATE"] as Timestamp,
                            createdDate = row["CREATEDDATE"] as Date
                    )
                }
        )
    }

    fun addSet(dataSource: DataSource, userId: String, inputSet: InputSet): Boolean {
        val params = mapOf(
                1 to System.nanoTime(),
                2 to userId,
                3 to inputSet.exercise,
                4 to inputSet.weight,
                5 to inputSet.reps,
                6 to LocalDateTime.now(),
                7 to LocalDate.now()
        )

        val results = MySQLJDBCUtil.doQuery(dataSource, insertSetQuery, params)
        println(results)
        return true
    }

    fun deleteSet(dataSource: DataSource, setId: String, userId: String): Boolean {
        val params = mapOf(
                1 to setId,
                2 to userId
        )

        val results = MySQLJDBCUtil.doQuery(dataSource, deleteSetQuery, params)
        println(results)
        return true
    }

    fun updateSet(dataSource: DataSource, setId: String, userId: String, inputSet: InputSet): Boolean {
        val params = mapOf(
                1 to inputSet.exercise,
                2 to inputSet.weight,
                3 to inputSet.reps,
                4 to LocalDateTime.now(),
                5 to setId,
                6 to userId
        )

        val results = MySQLJDBCUtil.doQuery(dataSource, updateSetQuery, params)
        println(results)
        return true
    }
}