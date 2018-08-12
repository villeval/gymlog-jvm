package gymlog

import gymlog.controllers.GymLogController
import gymlog.implementations.SetsDatabase
import gymlog.models.Set
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import utils.TestUtils
import kotlin.test.assertEquals

@RunWith(SpringRunner::class)
@WebMvcTest(value = [(GymLogController::class)], secure = false)
class GymLogTest {

    @Autowired
    private var mockMvc: MockMvc? = null

    @MockBean
    private var setsDatabase: SetsDatabase? = null

    private val mockSets = listOf(
            Set(1, "Ville123", 100, "Squat", 6),
            Set(2, "Ville123", 100, "Squat", 6),
            Set(3, "Ville123", 100, "Squat", 6)
    )

    @Ignore
    @Throws(Exception::class)
    @Test
    fun exampleTest() {
        //Mockito.`when`(setsDatabase!!.getSets(Mockito.anyString())).thenReturn(mockSets)
        val requestBuilder = MockMvcRequestBuilders.get("/gymlog/sets?userId=Ville123").accept(MediaType.APPLICATION_JSON)
        val result = mockMvc!!.perform(requestBuilder).andReturn()
        println(result.response.contentAsString)
        val expected = TestUtils.getSampleJsonAsString("get-sets-example.json")
        assertEquals(expected, result.response.contentAsString)
    }
}