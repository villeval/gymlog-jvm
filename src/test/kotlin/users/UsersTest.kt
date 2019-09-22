package users

import gymlog.Application

import gymlog.controllers.UsersController
import gymlog.utils.DatabaseUtils
import gymlog.models.User
import gymlog.models.UserErrors
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
import gymlog.services.SetsService.CREATED_DATE_COLUMN
import gymlog.services.SetsService.EXERCISE_COLUMN
import gymlog.services.SetsService.SETS_TABLE
import gymlog.services.SetsService.SET_ID_COLUMN
import gymlog.services.SetsService.REPETITIONS_COLUMN
import gymlog.services.SetsService.USER_ID_COLUMN
import gymlog.services.SetsService.WEIGHT_COLUMN
import gymlog.utils.JsonUtils
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import utils.InvokeActions
import utils.InvokeActions.invokeAuthentication
import utils.InvokeActions.invokeGetWithAuth
import utils.InvokeActions.invokePost
import utils.TestDbUtils
import kotlin.collections.ArrayList

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [Application::class])
@AutoConfigureMockMvc
@TestPropertySource(locations = ["classpath:junit.properties"])
class UsersTest {

    @Autowired
    val usersController: UsersController? = null

    @Autowired
    private val mvc: MockMvc? = null

    @Autowired
    @Qualifier("gymlogdatasource")
    private val gymlogDataSource: DataSource? = null

    private val log = LoggerFactory.getLogger(UsersTest::class.java)

    @Before
    fun init() {
        TestDbUtils.createSchema(gymlogDataSource, "gymlog_db")
        TestDbUtils.executeSql(gymlogDataSource, "CREATE TABLE IF NOT EXISTS $SETS_TABLE ($SET_ID_COLUMN varchar(100), $USER_ID_COLUMN varchar(100), $EXERCISE_COLUMN varchar(100), $WEIGHT_COLUMN decimal(10,1), $REPETITIONS_COLUMN integer, $CREATED_DATE_COLUMN timestamp);")
        TestDbUtils.executeSql(gymlogDataSource, "INSERT INTO $SETS_TABLE VALUES ('set id 1', 'user id 1', 'Squat', 102.5, 10, '2019-01-01 00:00:00');")
        TestDbUtils.executeSqlFile(gymlogDataSource, "create-auth-tables.sql")
    }

    @Test
    @Throws(Exception::class)
    fun contextLoads() {
        Assert.assertTrue(usersController != null)
        Assert.assertTrue(gymlogDataSource != null)
        Assert.assertTrue(mvc != null);
    }

    @Test
    fun testRegisterUser() {
        val body = JsonUtils.objectToJson(User("johndoe", "password"))
        val result = invokePost(mvc!!, "/register", body = body, pathVariables = ArrayList()).andExpect(status().isOk).andReturn()
        val responseUser = JsonUtils.jsonToObject(result.response.contentAsString, User::class.java)
        Assert.assertEquals("johndoe", responseUser.username)
        Assert.assertEquals("password", responseUser.password)

        val foundRows = DatabaseUtils.doQuery(gymlogDataSource!!, "select * from gymlog_db.users where username = ?", mapOf(1 to "johndoe"))
        println(foundRows)
        Assert.assertEquals(1, foundRows.size)
    }

    @Test
    fun testRegisterUserAlreadyExists() {
        val body = JsonUtils.objectToJson(User("user", "password"))
        val result = invokePost(mvc!!, "/register", body = body, pathVariables = ArrayList()).andExpect(status().isOk).andReturn()
        val response = JsonUtils.jsonToObject(result.response.contentAsString, UserErrors::class.java)
        println(response)
        Assert.assertSame(UserErrors::class.java, response::class.java)
        Assert.assertEquals("Username already exists", response.username)

        val foundRows = DatabaseUtils.doQuery(gymlogDataSource!!, "select * from gymlog_db.users where username = ?", mapOf(1 to "user"))
        println(foundRows)
        Assert.assertEquals(1, foundRows.size)
    }

    @Test
    fun testHeartbeatWithRegisterUser() {
        val body = JsonUtils.objectToJson(User("johndoe", "password"))
        invokePost(mvc!!, "/register", body = body, pathVariables = ArrayList()).andExpect(status().isOk).andReturn()
        val foundRows = DatabaseUtils.doQuery(gymlogDataSource!!, "select * from gymlog_db.users", emptyMap())
        println(foundRows)

        val result = InvokeActions.invokeGetWithAuth(mvc, "/api/heartbeat", token = getToken("johndoe", "password")).andExpect(status().isOk).andReturn()
        val response = JsonUtils.jsonToObject(result.response.contentAsString, HashMap::class.java)
        println(response)
        Assert.assertEquals(200, result.response.status)
        Assert.assertEquals("ok", response["status"] as String)
    }

    private fun getToken(user: String, password: String): String {
        val body = JsonUtils.objectToJson(mapOf("username" to user, "password" to password))
        val tokenResult = invokeAuthentication(mvc!!, "/login", body = body).andExpect(status().isOk).andReturn()
        return (tokenResult.response.getHeaderValue("Authorization") as String).substring(6)
    }
}