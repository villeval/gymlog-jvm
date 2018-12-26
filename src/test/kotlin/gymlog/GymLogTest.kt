package gymlog

import gymlog.interfaces.SetsInterface
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class GymLogTest {

    @Autowired
    val setsInterface: SetsInterface? = null

    @Test
    @Throws(Exception::class)
    fun contextLoads() {
        Assert.assertTrue(setsInterface != null)
    }
}