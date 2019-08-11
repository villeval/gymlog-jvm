package utils

import gymlog.services.SetsDatabase
import gymlog.utils.DatabaseUtils
import javax.sql.DataSource
import javax.xml.validation.Schema

object TestDbUtils {

    fun createSchema(dataSource: DataSource?, schema: String) {
        if(dataSource == null) throw Exception("Data source not found")
        val conn = DatabaseUtils.getConnection(dataSource)
        conn.use {
            conn.prepareStatement("DROP SCHEMA $schema IF EXISTS CASCADE;").executeUpdate()
            conn.prepareStatement("CREATE SCHEMA FOO AUTHORIZATION DBA;").executeUpdate()
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