package sets

import gymlog.Application
import sets.InvokeActions.invokeDelete
import gymlog.controllers.SetsController
import gymlog.utils.DatabaseUtils
import gymlog.models.Sets.SetRow
import gymlog.models.Sets.Sets
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
import sets.InvokeActions.invokeGet
import sets.InvokeActions.invokePost
import gymlog.services.SetsDatabase.CREATED_DATE_COLUMN
import gymlog.services.SetsDatabase.EXERCISE_COLUMN
import gymlog.services.SetsDatabase.SETS_TABLE
import gymlog.services.SetsDatabase.SET_ID_COLUMN
import gymlog.services.SetsDatabase.REPETITIONS_COLUMN
import gymlog.services.SetsDatabase.USER_ID_COLUMN
import gymlog.services.SetsDatabase.WEIGHT_COLUMN
import gymlog.utils.JsonUtils
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import utils.TestDbUtils
import java.math.BigDecimal
import java.util.*
import kotlin.collections.HashMap

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [Application::class])
@AutoConfigureMockMvc
@TestPropertySource(locations = ["classpath:junit.properties"])
class SetsTest {

    @Autowired
    val setsController: SetsController? = null

    @Autowired
    private val mvc: MockMvc? = null

    @Autowired
    @Qualifier("gymlogdatasource")
    private val gymlogDataSource: DataSource? = null

    private val log = LoggerFactory.getLogger(SetsTest::class.java)

    @Before
    fun init() {
        TestDbUtils.createSchema(gymlogDataSource, "gymlog_db")
        TestDbUtils.executeSql(gymlogDataSource, "CREATE TABLE IF NOT EXISTS $SETS_TABLE ($SET_ID_COLUMN varchar(100), $USER_ID_COLUMN varchar(100), $EXERCISE_COLUMN varchar(100), $WEIGHT_COLUMN decimal(10,1), $REPETITIONS_COLUMN integer, $CREATED_DATE_COLUMN timestamp);")
        TestDbUtils.executeSql(gymlogDataSource, "INSERT INTO $SETS_TABLE VALUES ('set id 1', 'user id 1', 'Squat', 102.5, 10, '2019-01-01 00:00:00');")
    }

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
        val response = JsonUtils.jsonToObject(result.response.contentAsString, Sets::class.java)
        val row = response.sets.first()
        Assert.assertEquals(1, response.total)
        Assert.assertEquals(200, result.response.status)
        Assert.assertEquals("set id 1", row.id)
        Assert.assertEquals("user id 1", row.userId)
        Assert.assertEquals(10, row.repetitions)
        Assert.assertEquals("Squat", row.exercise)
        Assert.assertEquals(BigDecimal(102.5), row.weight)
    }

    @Test
    fun testDeleteSet() {
        val result = invokeDelete(mvc!!, "/api/sets/{userId}/{setId}", pathVariables = arrayListOf("user id 1", "set id 1")).andExpect(status().isOk).andReturn()
        val responseSet = JsonUtils.jsonToObject(result.response.contentAsString, SetRow::class.java)
        Assert.assertEquals("user id 1", responseSet.userId)
        Assert.assertEquals("set id 1", responseSet.id)

        val foundRows = DatabaseUtils.doQuery(gymlogDataSource!!, "select * from $SETS_TABLE where $USER_ID_COLUMN = ? and $SET_ID_COLUMN = ?", mapOf(1 to "user id 1", 2 to "set id 1"))
        Assert.assertEquals(0, foundRows.size)
    }

    @Test
    fun testPostSet() {
        val body = JsonUtils.objectToJson(SetRow(null, "user id 1", BigDecimal(105.0), "Deadlift", 15, Date(System.currentTimeMillis())))
        val result = invokePost(mvc!!, "/api/sets/{userId}", pathVariables = arrayListOf("user id 1"), body = body).andExpect(status().isOk).andReturn()
        val responseSet = JsonUtils.jsonToObject(result.response.contentAsString, SetRow::class.java)
        Assert.assertNotNull(responseSet.id)
        Assert.assertEquals("user id 1", responseSet.userId)
        Assert.assertEquals(BigDecimal(105.0), responseSet.weight)
        Assert.assertEquals("Deadlift", responseSet.exercise)
        Assert.assertEquals(15, responseSet.repetitions)
        Assert.assertNotNull(responseSet.createdDate)

        val foundRows = DatabaseUtils.doQuery(gymlogDataSource!!, "select * from $SETS_TABLE where $USER_ID_COLUMN = ?", mapOf(1 to "user id 1"))
        Assert.assertEquals(2, foundRows.size)
    }

    // todo bad case tests (invalid input etc.)
}