package gymlog

import gymlog.controllers.SetsController
import gymlog.utils.DatabaseUtils
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import javax.sql.DataSource
import org.springframework.test.web.servlet.MockMvc
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
//@ActiveProfiles(“test”)
@TestPropertySource(locations = ["classpath:junit.properties"])
class GymLogTest {

    @Autowired
    val setsController: SetsController? = null

    private val log = LoggerFactory.getLogger(GymLogTest::class.java)

    @Autowired
    private val mvc: MockMvc? = null

    private val mapper = ObjectMapper()

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

    @Throws(Exception::class)
    fun invokeHeartbeat(): ResultActions {
        return mvc!!.perform(get("/api/heartbeat").accept(MediaType.APPLICATION_JSON))
    }

    // todo: tests for each crud action / API
    // todo: tear down after execution (if http server will be added)

    @Test
    @Throws(Exception::class)
    fun contextLoads() {
        Assert.assertTrue(setsController != null)
        Assert.assertTrue(gymlogDataSource != null)
        Assert.assertTrue(mvc != null);
    }

    @Test
    fun testHeartbeat() {
        val result = invokeHeartbeat()
                .andExpect(status().isOk).andReturn();
        println(result.response.contentAsString)
    }
}