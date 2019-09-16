package utils

import gymlog.utils.DatabaseUtils
import java.io.File
import javax.sql.DataSource
import org.hsqldb.cmdline.SqlFile

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

    fun executeSqlFile(dataSource: DataSource?, file: String) {
        if(dataSource == null) throw Exception("Data source not found")
        val conn = DatabaseUtils.getConnection(dataSource)
        conn.use {
            val sqlFile = SqlFile(File("src/test/resources/database/$file"), "UTF-8")
            sqlFile.connection = conn
            sqlFile.execute()
        }
    }
}