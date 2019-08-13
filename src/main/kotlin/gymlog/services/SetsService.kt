package gymlog.services

import gymlog.exceptions.DatabaseOperationFailedException
import gymlog.exceptions.InvalidInputException
import gymlog.models.Sets.Sets
import gymlog.models.Sets.SetRow
import gymlog.utils.DatabaseUtils
import java.math.BigDecimal
import java.sql.*
import javax.sql.DataSource

object SetsService {

    // table properties
    const val SETS_TABLE = "gymlog_db.SETS"

    // columns
    const val SET_ID_COLUMN = "SET_ID"
    const val USER_ID_COLUMN = "USER_ID"
    const val WEIGHT_COLUMN = "WEIGHT"
    const val EXERCISE_COLUMN = "EXERCISE"
    const val REPETITIONS_COLUMN = "REPETITIONS"
    const val CREATED_DATE_COLUMN = "CREATED_DATE"

    private const val getSetsQuery = "SELECT * FROM $SETS_TABLE WHERE $USER_ID_COLUMN LIKE ? LIMIT ?, ?;"
    private const val insertSetQuery = "INSERT INTO $SETS_TABLE VALUES (?,?,?,?,?,?);"
    private const val deleteSetQuery = "DELETE FROM $SETS_TABLE WHERE $SET_ID_COLUMN = ?;"

    fun getSets(dataSource: DataSource, userId: String, skip: Int, limit: Int): Sets {
        val params = mapOf(
                1 to userId,
                2 to skip,
                3 to limit
        )

        val results = DatabaseUtils.doQuery(dataSource, getSetsQuery, params)
        return Sets(
                total = results.size,
                skip = skip,
                limit = limit,
                sets = results.map { row ->
                    SetRow(
                            id = row[SET_ID_COLUMN] as String,
                            userId = row[USER_ID_COLUMN] as String,
                            weight = row[WEIGHT_COLUMN] as BigDecimal,
                            exercise = row[EXERCISE_COLUMN] as String,
                            repetitions = row[REPETITIONS_COLUMN] as Int,
                            createdDate = convertTimestampToDate(row[CREATED_DATE_COLUMN] as Timestamp)
                    )
                }
        )
    }

    fun addSet(dataSource: DataSource, userId: String, inputSet: SetRow): SetRow {
        if(inputSet.exercise == null || inputSet.weight == null || inputSet.repetitions == null) throw InvalidInputException()
        val setId = System.nanoTime().toString() + userId
        val createdDate = java.util.Date(System.currentTimeMillis())
        val params = mapOf(
                1 to setId,
                2 to userId,
                3 to inputSet.exercise,
                4 to inputSet.weight,
                5 to inputSet.repetitions,
                6 to createdDate
        )
        val result = DatabaseUtils.doUpdate(dataSource, insertSetQuery, params)
        return if (result == 1) SetRow(setId, userId, inputSet.weight, inputSet.exercise, inputSet.repetitions, createdDate) else throw DatabaseOperationFailedException()
    }

    fun deleteSet(dataSource: DataSource, setId: String): SetRow {
        val params = mapOf(1 to setId)
        val result = DatabaseUtils.doUpdate(dataSource, deleteSetQuery, params)
        // todo: add user id here after authentication is implemented?
        return if (result == 1) SetRow(setId, null, null, null, null, null) else throw DatabaseOperationFailedException()
    }

    private fun convertTimestampToDate(timestamp: Timestamp): Date {
        return Date(timestamp.time)
    }
}