package gymlog.utils

import java.sql.*
import javax.sql.DataSource

object MySQLJDBCUtil {

    fun getResults(dataSource: DataSource, query: String, params: Map<Int, Any>): List<Map<*,*>> {
        val results = mutableListOf<MutableMap<String, Any?>>()
        val connection = MySQLJDBCUtil.getConnection(dataSource)

        connection.use {
            val preparedStatement = connection.prepareStatement(query)
            preparedStatement.use {
                params.forEach { param ->
                    when (param.value::class.java){
                        String::class.java -> preparedStatement.setString(param.key, param.value as String)
                        Integer::class.java -> preparedStatement.setInt(param.key, param.value as Int)
                        java.util.Date::class.java -> {
                            val date = param.value as Date
                            preparedStatement.setTimestamp(param.key, Timestamp(date.time))
                        }
                        else -> println("Parameter data type not valid")
                    }
                }
                val resultSet = preparedStatement.executeQuery()
                val columnCount = resultSet.metaData.columnCount

                while(resultSet.next()) {
                    val row = mutableMapOf<String, Any?>()
                    for(index in 1..columnCount) {
                        val type = resultSet.metaData.getColumnClassName(index)
                        val columnName = resultSet.metaData.getColumnName(index)
                        @Suppress("IMPLICIT_CAST_TO_ANY")
                        val value = when (type) {
                            "java.lang.Integer" -> resultSet.getInt(index)
                            "java.sql.Timestamp" -> resultSet.getTimestamp(index)
                            "java.lang.String" -> resultSet.getString(index)
                            "java.lang.Long" -> resultSet.getLong(index)
                            "java.lang.Double" -> resultSet.getDouble(index)
                            else -> null
                        }
                        row[columnName] = value
                    }
                    results.add(row)
                }
            }
        }
        return results.toList()
    }

    fun getConnection(dataSource: DataSource): Connection {
        var connection: Connection? = null
        try {
            connection = dataSource.connection
        } catch (ex: SQLException) {
            ex.printStackTrace()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return connection ?: throw Exception("Connection to database failed")
    }
}