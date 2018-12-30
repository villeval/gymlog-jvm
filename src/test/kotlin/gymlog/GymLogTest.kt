package gymlog

import gymlog.interfaces.SetsInterface
import gymlog.services.SetsDatabase
import gymlog.utils.DatabaseUtils
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import javax.sql.DataSource

@RunWith(SpringRunner::class)
@SpringBootTest
@TestPropertySource(locations = ["classpath:junit.properties"])
class GymLogTest {

    @Autowired
    val setsInterface: SetsInterface? = null

    @Autowired
    @Qualifier("gymlogdatasource")
    private val gymlogDataSource: DataSource? = null

    @Before
    fun init() {
        val conn = DatabaseUtils.getConnection(gymlogDataSource!!)
        conn.use {
            val initSchema = conn.prepareStatement("CREATE SCHEMA FOO;")
            println(initSchema.executeUpdate())
            val initTable = conn.prepareStatement("CREATE TABLE FOO.SETS (ID BIGINT, USERID INTEGER, EXERCISE VARCHAR(100), WEIGHT DECIMAL, REPETITIONS INT, CREATEDDATE DATE, LASTMODIFIEDDATE TIMESTAMP);")
            println(initTable.executeUpdate())
            val insertData = conn.prepareStatement("INSERT INTO FOO.SETS VALUES (123, 1, 'Squat', 100.0, 10, '2018-12-31', '2018-12-31 12:05:01');")
            println(insertData.executeUpdate())
        }
    }

    // todo: tests for each crud action / API
    // todo: tear down after execution (if http server will be added)

    @Test
    @Throws(Exception::class)
    fun contextLoads() {
        Assert.assertTrue(setsInterface != null)
        Assert.assertTrue(gymlogDataSource != null)
    }
}