package utils

import gymlog.utils.DatabaseUtils
import javax.sql.DataSource

object TestDbUtils {

    fun createSchema(dataSource: DataSource?, schema: String) {
        if(dataSource == null) throw Exception("Data source not found")
        val conn = DatabaseUtils.getConnection(dataSource)
        conn.use {
            conn.prepareStatement("DROP SCHEMA $schema IF EXISTS CASCADE;").executeUpdate()
            conn.prepareStatement("CREATE SCHEMA $schema AUTHORIZATION DBA;").executeUpdate()
        }
    }

    fun executeSql(dataSource: DataSource?, sql: String) {
        if(dataSource == null) throw Exception("Data source not found")
        val conn = DatabaseUtils.getConnection(dataSource)
        conn.use {
            conn.prepareStatement(sql).executeUpdate()
        }
    }
}