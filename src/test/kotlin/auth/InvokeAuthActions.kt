package auth

import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

object InvokeAuthActions {

    @Throws(Exception::class)
    fun invokeAuthentication(mvc: MockMvc, url: String, body: String): ResultActions {
        return mvc.perform(MockMvcRequestBuilders.post(url).content(body).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
    }
}