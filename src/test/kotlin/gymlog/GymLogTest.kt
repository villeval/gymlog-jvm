package gymlog

import gymlog.InvokeActions.invokeDeleteWithTwoPathVariables
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
import gymlog.InvokeActions.invokeGet
import gymlog.models.SetRows
import gymlog.services.SetsDatabase.CREATED_DATE_COLUMN
import gymlog.services.SetsDatabase.EXERCISE_COLUMN
import gymlog.services.SetsDatabase.SETS_TABLE
import gymlog.services.SetsDatabase.SET_ID_COLUMN
import gymlog.services.SetsDatabase.REPETITIONS_COLUMN
import gymlog.services.SetsDatabase.USER_ID_COLUMN
import gymlog.services.SetsDatabase.WEIGHT_COLUMN
import gymlog.utils.JsonUtils
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
//@ActiveProfiles(“test”)
@TestPropertySource(locations = ["classpath:junit.properties"])
class GymLogTest {

    @Autowired
    val setsController: SetsController? = null

    @Autowired
    private val mvc: MockMvc? = null

    @Autowired
    @Qualifier("gymlogdatasource")
    private val gymlogDataSource: DataSource? = null

    private val log = LoggerFactory.getLogger(GymLogTest::class.java)

    @Before
    fun init() {
        val conn = DatabaseUtils.getConnection(gymlogDataSource!!)
        conn.use {
            conn.prepareStatement("CREATE SCHEMA FOO;").executeUpdate()
            conn.prepareStatement("CREATE TABLE $SETS_TABLE ($SET_ID_COLUMN varchar(100), $USER_ID_COLUMN varchar(100), $EXERCISE_COLUMN varchar(100), $WEIGHT_COLUMN decimal(10,1), $REPETITIONS_COLUMN integer, $CREATED_DATE_COLUMN timestamp);").executeUpdate()
            conn.prepareStatement("INSERT INTO $SETS_TABLE VALUES ('set id 1', 'user id 1', 'Squat', 102.5, 10, '2019-01-01 00:00:00');").executeUpdate()
        }
    }

    // todo: tests for each crud action / API

    @Test
    @Throws(Exception::class)
    fun contextLoads() {
        Assert.assertTrue(setsController != null)
        Assert.assertTrue(gymlogDataSource != null)
        Assert.assertTrue(mvc != null);
    }

    @Test
    fun testHeartbeat() {
        val result = invokeGet(mvc!!, "/api/heartbeat").andExpect(status().isOk).andReturn()
        val response = JsonUtils.jsonToObject(result.response.contentAsString, HashMap::class.java)
        Assert.assertEquals(200, result.response.status)
        Assert.assertEquals("ok", response["status"] as String)
    }

    @Test
    fun testGetSets() {
        val result = invokeGet(mvc!!, "/api/sets", mapOf("userId" to "user id 1")).andExpect(status().isOk).andReturn()
        val response = JsonUtils.jsonToObject(result.response.contentAsString, SetRows::class.java)
        val row = response.sets.first()
        Assert.assertEquals(1, response.total)
        Assert.assertEquals(200, result.response.status)
        Assert.assertEquals("set id 1", row.id)
        Assert.assertEquals("user id 1", row.userId)
        Assert.assertEquals(10, row.reps)
        Assert.assertEquals("Squat", row.exercise)
        Assert.assertEquals(BigDecimal(102.5), row.weight)
    }

    // todo: should the database utils be switched to hsqldb tool with sql files
    @Test
    fun deleteSet() {
        invokeDeleteWithTwoPathVariables(mvc!!, "/api/sets/{userId}/{setId}", pathVariable1 =  "user id 1", pathVariable2 = "set id 1").andExpect(status().isOk).andReturn()
        val result = DatabaseUtils.doQuery(gymlogDataSource!!, "select * from $SETS_TABLE where $USER_ID_COLUMN = ? and $SET_ID_COLUMN = ?", mapOf(1 to "user id 1", 2 to "set id 1"))
        Assert.assertEquals(0, result.size)
    }
}