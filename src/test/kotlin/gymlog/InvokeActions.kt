package gymlog

import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import java.math.BigDecimal

object InvokeActions {

    @Throws(Exception::class)
    fun invokeGet(mvc: MockMvc, url: String, params: Map<String, Any> = emptyMap()): ResultActions {
        return if(params.isNotEmpty()) {
            val multiValueMap = convertParamsToMultiValueMap(params)
            mvc.perform(MockMvcRequestBuilders.get(url).params(multiValueMap).accept(MediaType.APPLICATION_JSON))
            } else {
            mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON))
        }
    }

    @Throws(Exception::class)
    fun invokePost(mvc: MockMvc, url: String, json: ByteArray): ResultActions {
        return mvc.perform(post(url).content(json).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
    }

    @Throws(Exception::class)
    fun invokeDeleteWithTwoPathVariables(mvc: MockMvc, url: String, params: Map<String, Any> = emptyMap(), pathVariable1: Any, pathVariable2: Any): ResultActions {
        return if(params.isNotEmpty()) {
            val multiValueMap = convertParamsToMultiValueMap(params)
            mvc.perform(MockMvcRequestBuilders.delete(url, pathVariable1, pathVariable2).params(multiValueMap).accept(MediaType.APPLICATION_JSON))
        } else {
            mvc.perform(MockMvcRequestBuilders.delete(url, pathVariable1, pathVariable2).accept(MediaType.APPLICATION_JSON))
        }
    }

    private fun convertParamsToMultiValueMap(params: Map<String, Any>): MultiValueMap<String, String> {
        val multiValueMap: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.map { param ->
            val valueAsString = if(param.value !is String) param.value.toString() else param.value as String
            multiValueMap.add(param.key, valueAsString)
        }
        return multiValueMap
    }

}