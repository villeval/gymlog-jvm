package gymlog.services

import gymlog.models.InputSet
import gymlog.models.SetRow
import gymlog.models.SetRows
import gymlog.utils.DatabaseUtils
import java.math.BigDecimal
import java.sql.*
import java.time.LocalDate
import java.time.LocalDateTime
import javax.sql.DataSource

object SetsDatabase {

    // table properties
    const val SETS_TABLE = "FOO.SETS"

    // columns
    const val SET_ID_COLUMN = "SET_ID"
    const val USER_ID_COLUMN = "USER_ID"
    const val WEIGHT_COLUMN = "WEIGHT"
    const val EXERCISE_COLUMN = "EXERCISE"
    const val REPETITIONS_COLUMN = "REPETITIONS"
    const val CREATED_DATE_COLUMN = "CREATED_DATE"

    private const val getSetsQuery = "SELECT * FROM $SETS_TABLE WHERE $USER_ID_COLUMN LIKE ? LIMIT ?, ?"
    private const val insertSetQuery = "INSERT INTO $SETS_TABLE VALUES (?,?,?,?,?,?,?)"
    private const val deleteSetQuery = "DELETE FROM $SETS_TABLE WHERE $SET_ID_COLUMN = ? AND $USER_ID_COLUMN = ?"

    fun getSets(dataSource: DataSource, userId: String, skip: Int, limit: Int): SetRows {
        val params = mapOf(
                1 to userId,
                2 to skip,
                3 to limit
        )

        val results = DatabaseUtils.doQuery(dataSource, getSetsQuery, params)
        return SetRows(
                total = results.size,
                skip = skip,
                limit = limit,
                sets = results.map { row ->
                    SetRow(
                        id = row[SET_ID_COLUMN] as String,
                        userId = row[USER_ID_COLUMN] as String,
                        weight = row[WEIGHT_COLUMN] as BigDecimal,
                        exercise = row[EXERCISE_COLUMN] as String,
                        reps = row[REPETITIONS_COLUMN] as Int,
                        createdDate = convertTimestampToDate(row[CREATED_DATE_COLUMN] as Timestamp)
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
                6 to LocalDate.now(),
                7 to LocalDateTime.now()
        )

        val results = DatabaseUtils.doUpdate(dataSource, insertSetQuery, params)
        // todo handling result
        println(results)
        return true
    }

    fun deleteSet(dataSource: DataSource, setId: String, userId: String): Boolean {
        val params = mapOf(
                1 to setId,
                2 to userId
        )

        val results = DatabaseUtils.doUpdate(dataSource, deleteSetQuery, params)
        // todo handling result
        println(results)
        return true
    }

    private fun convertTimestampToDate(timestamp: Timestamp): Date {
        return Date(timestamp.time)
    }
}