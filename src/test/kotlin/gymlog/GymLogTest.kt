package gymlog

import gymlog.interfaces.SetsInterface
import gymlog.utils.DatabaseUtils
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import javax.sql.DataSource

@RunWith(SpringRunner::class)
@SpringBootTest
class GymLogTest {

    @Autowired
    val setsInterface: SetsInterface? = null

    @Autowired
    @Qualifier("gymlogdatasource")
    private val gymlogDataSource: DataSource? = null

    // todo: init database
    // todo: execute schema, table and data inserts
    // todo: tests for each crud action / API
    // todo: tear down after execution

    @Test
    @Throws(Exception::class)
    fun contextLoads() {
        Assert.assertTrue(setsInterface != null)
        Assert.assertTrue(gymlogDataSource != null)
    }
}